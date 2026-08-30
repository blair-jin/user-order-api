package io.github.blairjeon.userorderapi.dto.order;

import io.github.blairjeon.userorderapi.domain.order.Order;
import io.github.blairjeon.userorderapi.domain.order.OrderStatus;

public record OrderResponse(
        Long id,
        long totalPrice,
        OrderStatus orderStatus
) {
    public static OrderResponse from(Order order){
        return new OrderResponse(
              order.getId(),
              order.getTotalPrice(),
              order.getOrderStatus()
        );
    }
}