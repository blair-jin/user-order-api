package io.github.blairjeon.userorderapi.exception.BAD_REQUEST;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException() {
        super("EMPTY_CART");
    }
    public EmptyCartException(String message) {
        super(message);
    }
}
