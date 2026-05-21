package com.example.userorder.domain.user.vo;

import com.example.userorder.common.StringValidator;

public record RawPassword(
        String value
) {
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 50;

    public RawPassword {
        StringValidator.validateMinLength(value, MIN_LENGTH);
        StringValidator.validateMaxLength(value, MAX_LENGTH);
    }

    public static RawPassword of(String value) {
        return new RawPassword(value);
    }
}