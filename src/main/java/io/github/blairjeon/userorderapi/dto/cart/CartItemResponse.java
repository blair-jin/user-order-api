package io.github.blairjeon.userorderapi.dto.cart;

import io.github.blairjeon.userorderapi.domain.cart.Cart;
import io.github.blairjeon.userorderapi.domain.cart.CartItem;
import io.github.blairjeon.userorderapi.domain.product.Product;

public record CartItemResponse(
        Long productId,
        Long cartItemId,
        String productName,
        long unitPrice,
        int quantity
) {
    public static CartItemResponse from(Product product, CartItem cartItem){
        return new CartItemResponse(
                product.getId(),
                cartItem.getId(),
                product.getProductName(),
                product.getUnitPrice(),
                cartItem.getOrderQuantity()
        );
    }
}