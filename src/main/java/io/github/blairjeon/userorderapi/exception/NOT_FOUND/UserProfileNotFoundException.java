package io.github.blairjeon.userorderapi.exception.NOT_FOUND;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException() {
        super("USER_PROFILE_NOT_FOUND");
    }
    public UserProfileNotFoundException(String message) {
        super(message);
    }
}