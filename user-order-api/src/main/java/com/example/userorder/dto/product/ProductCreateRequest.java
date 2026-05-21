package com.example.userorder.dto.product;

public record ProductCreateRequest(
        String name,
        int stockQuantity,
        long unitPrice
) {
}