package com.example.userorder.domain.user.vo;

import java.time.LocalDate;
import java.util.Objects;

public record BirthDate(
        LocalDate value
) {
    private final static int MIN_AGE = 14;

    public BirthDate {
        Objects.requireNonNull(value);
        validateNotFuture(value);
        validateMinAge(value);
    }

    public static BirthDate of(LocalDate value) {
        return new BirthDate(value);
    }

    private static void validateNotFuture(LocalDate value) {
        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("BIRTH_DATE_CANNOT_BE_IN_FUTURE");
        }
    }

    private static void validateMinAge(LocalDate value) {
        if (value.isAfter(LocalDate.now().minusYears(MIN_AGE))) {
            throw new IllegalArgumentException("AGE_BELOW_MINIMUM");
        }
    }
}