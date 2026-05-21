package com.example.userorder.dto.product;

import com.example.userorder.domain.product.Product;

public record ProductResponse(
        Long productId,
        String name,
        int stockQuantity,
        long unitPrice
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getStockQuantity(),
                product.getUnitPrice()
        );
    }
}