package io.github.blairjeon.userorderapi.exception.BAD_REQUEST;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException() {
        super("INVALID_ORDER_STATUS");
    }
    public InvalidOrderStatusException(String message) {
        super(message);
    }
}
