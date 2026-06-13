package com.example.takeout.model;

import java.math.BigDecimal;

public record MenuItem(
        long id,
        String name,
        String description,
        String category,
        BigDecimal price,
        String imageUrl,
        int monthlySales,
        boolean signature
) {
}

