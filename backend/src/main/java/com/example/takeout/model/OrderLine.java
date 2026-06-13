package com.example.takeout.model;

import java.math.BigDecimal;

public record OrderLine(
        long menuItemId,
        String name,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}

