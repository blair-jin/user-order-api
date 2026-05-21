package com.example.userorder.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemAddRequest(
        @NotNull(message = "Product ID is required")
        Long productId,

        @Positive
        int orderQuantity
) {
}