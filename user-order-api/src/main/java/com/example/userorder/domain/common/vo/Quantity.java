package com.example.userorder.domain.common.vo;

import com.example.userorder.common.NumberValidator;

public record Quantity(
        int value
) {
    public Quantity {
        NumberValidator.validateNonNegative(value);
    }

    public static Quantity of(int value) {
        return new Quantity(value);
    }

    public Quantity add(int value) {
        return Quantity.of(this.value + value);
    }

    public Quantity subtract(Quantity value) {
        return Quantity.of(this.value - value.value);
    }
}