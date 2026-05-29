package com.example.userorder.domain.user.vo;

import com.example.userorder.common.StringValidator;

public record LoginId(
        String value
) {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 20;

    public LoginId {
        StringValidator.validateMinLength(value, MIN_LENGTH);
        StringValidator.validateMaxLength(value, MAX_LENGTH);
    }

    public static LoginId of(String value) {
        return new LoginId(value);
    }
}