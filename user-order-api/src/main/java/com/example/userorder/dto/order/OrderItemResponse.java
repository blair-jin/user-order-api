package com.example.userorder.dto.order;

import com.example.userorder.domain.order.OrderItem;

public record OrderItemResponse(
        Long id,
        Long orderId,
        Long productId,
        String productName,
        int orderQuantity,
        long unitPrice,
        long totalPrice
) {
    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getOrderQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getUnitPrice() * orderItem.getOrderQuantity()
        );
    }
}