package com.example.userorder.domain.common.vo;

import com.example.userorder.common.NumberValidator;

public record Money(
        long value
) {
    public Money {
        NumberValidator.validateNonNegative(value);
    }

    public static Money of(long value) {
        return new Money(value);
    }

    public Money add(Money value) {
        return Money.of(this.value + value.value);
    }

    public Money subtract(Money value) {
        return Money.of(this.value - value.value);
    }

    public Money multiply(long value) {
        return Money.of(this.value * value);
    }

    public Money multiply(Quantity value) {
        return Money.of(this.value * value.value());
    }
}