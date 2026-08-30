package io.github.blairjeon.userorderapi.exception.NOT_FOUND;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("CART_NOT_FOUND");
    }
    public CartNotFoundException(String message) {
        super(message);
    }
}