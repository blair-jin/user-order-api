package io.github.blairjeon.userorderapi.dto.product;

import io.github.blairjeon.userorderapi.domain.product.ProductStatus;

public record ProductUpdateRequest(
        String productName,
        Integer stockQuantity,
        Long unitPrice,
        ProductStatus productStatus
) {
}