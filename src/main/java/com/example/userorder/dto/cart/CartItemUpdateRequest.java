package com.example.userorder.dto.cart;

import jakarta.validation.constraints.Positive;

public record CartItemUpdateRequest(
        @Positive
        int orderQuantity
) {
}