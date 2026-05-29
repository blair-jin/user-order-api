package com.example.userorder.common.exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException() {
        super("ORDER_ITEM_NOT_FOUND");
    }

    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
