package com.example.userorder.domain.user.vo;

import java.util.Objects;

public record Password(
        String value
) {
    public Password {
        Objects.requireNonNull(value);
    }

    public static Password of(String value) {
        return new Password(value);
    }
}