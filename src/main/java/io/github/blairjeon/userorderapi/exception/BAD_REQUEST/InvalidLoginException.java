package io.github.blairjeon.userorderapi.exception.BAD_REQUEST;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("INVALID_LOGIN");
    }
    public InvalidLoginException(String message) {
        super(message);
    }
}
