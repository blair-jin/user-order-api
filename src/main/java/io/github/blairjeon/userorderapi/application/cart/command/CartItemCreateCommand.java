package io.github.blairjeon.userorderapi.application.cart.command;

import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;

public record CartItemCreateCommand(
        Long productId,
        OrderQuantity quantity
) {
}