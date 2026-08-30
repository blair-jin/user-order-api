package io.github.blairjeon.userorderapi.domain.common.vo;

import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidValueException;

public record Money(
        long value
) {
    public Money{
        if(value <= 0){
            throw new InvalidValueException("Money", "Money cannot be negative.");
        }
    }

    public static Money of(long value){
        return new Money(value);
    }
}