package com.example.userorder.common.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException() {
        super("CART_ITEM_NOT_FOUND");
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }
}
