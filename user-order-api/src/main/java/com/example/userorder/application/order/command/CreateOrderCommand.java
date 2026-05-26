package com.example.userorder.application.order.command;

import com.example.userorder.domain.common.vo.Quantity;

public record CreateOrderCommand(
        Long productId,
        Quantity orderQuantity
) {
}