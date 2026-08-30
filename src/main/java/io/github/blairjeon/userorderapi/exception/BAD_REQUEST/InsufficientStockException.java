package io.github.blairjeon.userorderapi.exception.BAD_REQUEST;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Insufficient_Stock");
    }
    public InsufficientStockException(String message) {
        super(message);
    }
}
