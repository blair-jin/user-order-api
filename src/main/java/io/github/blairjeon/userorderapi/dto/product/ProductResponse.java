package io.github.blairjeon.userorderapi.dto.product;

import io.github.blairjeon.userorderapi.domain.product.Product;
import io.github.blairjeon.userorderapi.domain.product.ProductStatus;
import io.github.blairjeon.userorderapi.domain.user.vo.UserName;

public record ProductResponse(
        Long productId,
        String userName,
        String productName,
        int stockQuantity,
        long unitPrice,
        ProductStatus productStatus
) {
    public static ProductResponse from(Product product, UserName userName){
        return new ProductResponse(
                product.getId(),
                userName.value(),
                product.getProductName(),
                product.getStockQuantity(),
                product.getUnitPrice(),
                product.getProductStatus()
        );
    }
}