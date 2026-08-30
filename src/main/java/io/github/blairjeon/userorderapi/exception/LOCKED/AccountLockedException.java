package io.github.blairjeon.userorderapi.exception.LOCKED;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
        super("ACCOUNT_LOCKED");
    }
    public AccountLockedException(String message) {
        super(message);
    }
}
