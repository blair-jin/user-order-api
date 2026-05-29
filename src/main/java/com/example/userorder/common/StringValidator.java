package com.example.userorder.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringValidator {
    private static final int SYSTEM_MAX_LENGTH = 255;

    public static void validate(String value) {
        if (value == null) {
            throw new IllegalArgumentException("VALUE_MUST_NOT_BE_NULL");
        }
        if (value.isBlank()) {
            throw new IllegalArgumentException("VALUE_MUST_NOT_BE_BLANK");
        }
        if (value.length() > SYSTEM_MAX_LENGTH) {
            throw new IllegalArgumentException("OUT_OF_SYSTEM_MAX_LENGTH");
        }
    }

    public static void validateMinLength(String value, int min) {
        validate(value);

        if (value.length() < min) {
            throw new IllegalArgumentException("LENGTH_BELOW_MIN");
        }
    }

    public static void validateMaxLength(String value, int max) {
        validate(value);

        if (value.length() > max) {
            throw new IllegalArgumentException("LENGTH_ABOVE_MAX");
        }
    }
}