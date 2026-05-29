package com.example.userorder.common.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("CART_NOT_FOUND");
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}
