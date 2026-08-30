package io.github.blairjeon.userorderapi.domain.order.vo;

import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidValueException;

public record OrderQuantity(
        int value
) {
    public OrderQuantity{
        if (value <= 0) {
            throw new InvalidValueException(
                    "OrderQuantity", "Order quantity must be greater than zero."
            );
        }
    }

    public static OrderQuantity of(int value){
        return new OrderQuantity(value);
    }
}