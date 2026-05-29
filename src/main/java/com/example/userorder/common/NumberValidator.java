package com.example.userorder.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberValidator {
    public static void validatePositive(long value) {
        if (value < 1) {
            throw new IllegalArgumentException("VALUE_MUST_BE_POSITIVE");
        }
    }

    public static void validateNonNegative(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("VALUE_MUST_BE_NON_NEGATIVA");
        }
    }

    public static void validateGoe(long value, long min) {
        if (value < min) {
            throw new IllegalArgumentException("VALUE_BELOW_MIN");
        }
    }

    public static void validateLoe(long value, long max) {
        if (value > max) {
            throw new IllegalArgumentException("VALUE_ABOVE_MAX");
        }
    }
}