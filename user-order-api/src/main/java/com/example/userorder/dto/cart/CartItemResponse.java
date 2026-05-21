package com.example.userorder.dto.cart;

import com.example.userorder.domain.cart.CartItem;
import com.example.userorder.domain.product.Product;

public record CartItemResponse(
        Long cartId,
        Long productId,
        String productName,
        int orderQuantity,
        long unitPrice,
        long totalPrice
) {
    public static CartItemResponse from(Long cartId, Product product, CartItem item) {
        return new CartItemResponse(
                cartId,
                product.getId(),
                product.getName(),
                item.getOrderQuantity(),
                product.getUnitPrice(),
                item.getOrderQuantity() * product.getUnitPrice()
        );
    }
}