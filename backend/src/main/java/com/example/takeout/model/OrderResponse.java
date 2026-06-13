package com.example.takeout.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        String orderNo,
        String status,
        Instant createdAt,
        BigDecimal totalAmount,
        int estimatedMinutes,
        List<OrderLine> items
) {
}

