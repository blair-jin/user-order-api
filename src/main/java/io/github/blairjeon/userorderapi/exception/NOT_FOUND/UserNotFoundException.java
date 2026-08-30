package io.github.blairjeon.userorderapi.exception.NOT_FOUND;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("USER_NOT_FOUND");
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}