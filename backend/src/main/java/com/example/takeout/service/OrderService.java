package com.example.takeout.service;

import com.example.takeout.model.MenuItem;
import com.example.takeout.model.OrderLine;
import com.example.takeout.model.OrderRequest;
import com.example.takeout.model.OrderResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class OrderService {
    private final CatalogService catalogService;

    public OrderService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public OrderResponse createOrder(OrderRequest request) {
        List<OrderLine> lines = request.items().stream()
                .map(item -> {
                    MenuItem menuItem = catalogService.findById(item.menuItemId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "菜单项不存在"));
                    BigDecimal subtotal = menuItem.price().multiply(BigDecimal.valueOf(item.quantity()));
                    return new OrderLine(menuItem.id(), menuItem.name(), item.quantity(), menuItem.price(), subtotal);
                })
                .toList();

        BigDecimal total = lines.stream()
                .map(OrderLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponse(
                "TO" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                "已接单",
                Instant.now(),
                total,
                35,
                lines
        );
    }
}

