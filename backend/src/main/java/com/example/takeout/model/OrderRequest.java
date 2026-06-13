package com.example.takeout.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrderRequest(
        @NotBlank String customerName,
        @NotBlank String phone,
        @NotBlank String address,
        @Size(max = 120) String note,
        @Valid @NotEmpty List<OrderItemRequest> items
) {
}

