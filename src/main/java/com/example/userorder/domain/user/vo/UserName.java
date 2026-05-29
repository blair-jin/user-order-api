package com.example.userorder.domain.user.vo;

import com.example.userorder.common.StringValidator;

public record UserName(
        String value
) {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;

    public UserName {
        StringValidator.validateMinLength(value, MIN_LENGTH);
        StringValidator.validateMaxLength(value, MAX_LENGTH);
    }

    public static UserName of(String name) {
        return new UserName(name);
    }
}