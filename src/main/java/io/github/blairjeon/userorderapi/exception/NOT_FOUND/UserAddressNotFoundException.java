package io.github.blairjeon.userorderapi.exception.NOT_FOUND;

public class UserAddressNotFoundException extends RuntimeException {
    public UserAddressNotFoundException() {
    super("USER_ADDRESS_NOT_FOUND");
  }
    public UserAddressNotFoundException(String message) {
        super(message);
    }
}
