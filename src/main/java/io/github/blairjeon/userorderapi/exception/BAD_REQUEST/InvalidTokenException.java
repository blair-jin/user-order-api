package io.github.blairjeon.userorderapi.exception.BAD_REQUEST;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("INVALID_TOKEN");
    }
    public InvalidTokenException(String message) {
        super(message);
    }
}
