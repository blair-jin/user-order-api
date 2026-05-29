package com.example.userorder.dto.product;

public record ProductUpdateRequest(
        String name,
        Integer stockQuantity,
        Long unitPrice
) {
}