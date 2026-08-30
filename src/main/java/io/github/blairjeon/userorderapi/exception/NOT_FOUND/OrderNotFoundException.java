package io.github.blairjeon.userorderapi.exception.NOT_FOUND;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("ORDER_NOT_FOUND");
    }
    public OrderNotFoundException(String message) {
        super(message);
    }
}