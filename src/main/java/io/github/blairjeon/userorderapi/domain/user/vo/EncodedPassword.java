package io.github.blairjeon.userorderapi.domain.user.vo;

import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidValueException;

import java.util.Objects;

public record EncodedPassword(
        String value
) {
    public EncodedPassword{
        if (value == null) {
            throw new InvalidValueException("EncodedPassword", "Encoded password cannot be null.");
        }

        if(value.isBlank()){
            throw new InvalidValueException("EncodedPassword", "Encoded password cannot be blank");
        }
    }

    public static EncodedPassword of(String value){
        return new EncodedPassword(value);
    }
}