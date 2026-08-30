package io.github.blairjeon.userorderapi.exception.CONFLICT;

public class DuplicateLoginIdException extends RuntimeException {
    public DuplicateLoginIdException() {
        super("DUPLICATE_LOGIN_ID");
    }
    public DuplicateLoginIdException(String message) {
        super(message);
    }
}
