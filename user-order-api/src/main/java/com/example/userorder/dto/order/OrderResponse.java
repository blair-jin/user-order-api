package com.example.userorder.dto.order;

import com.example.userorder.domain.order.Order;
import com.example.userorder.domain.order.OrderStatus;

public record OrderResponse(
        Long id,
        Long userId,
        long totalPrice,
        OrderStatus orderStatus
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getTotalPrice(),
                order.getOrderStatus()
        );
    }
}