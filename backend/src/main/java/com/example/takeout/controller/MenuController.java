package com.example.takeout.controller;

import com.example.takeout.model.MenuItem;
import com.example.takeout.service.CatalogService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final CatalogService catalogService;

    public MenuController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<MenuItem> listMenu() {
        return catalogService.listMenu();
    }
}

