package com.example.userorder.domain.user.vo;

import com.example.userorder.common.StringValidator;

public record Email(
        String value
) {
    public Email {
        StringValidator.validate(value);
        validateFormat(value);
    }

    public static Email of(String value) {
        return new Email(value);
    }

    private static void validateFormat(String value) {
        if (!value.contains("@")) {
            throw new IllegalArgumentException("INVALID_EMAIL_FORMAT");
        }
    }
}