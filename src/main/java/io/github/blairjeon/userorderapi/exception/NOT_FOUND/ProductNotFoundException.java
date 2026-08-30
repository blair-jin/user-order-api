package io.github.blairjeon.userorderapi.exception.NOT_FOUND;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("PRODUCT_NOT_FOUND");
    }
    public ProductNotFoundException(String message) {
        super(message);
    }
}