package io.github.blairjeon.userorderapi.domain.product.vo;

import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidValueException;

public record StockQuantity(
        int value
) {
    public StockQuantity {
        if (value < 0) {
            throw new InvalidValueException(
                    "StockQuantity", "Stock quantity cannot be negative."
            );
        }
    }

    public static StockQuantity of(int value){
        return new StockQuantity(value);
    }
}