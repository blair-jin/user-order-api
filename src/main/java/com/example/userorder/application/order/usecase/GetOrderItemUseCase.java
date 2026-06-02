package com.example.userorder.application.order.usecase;

import com.example.userorder.application.order.service.OrderService;
import com.example.userorder.dto.order.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOrderItemUseCase {
    private final OrderService orderService;

    public OrderItemResponse execute(Long userId, Long orderId, Long orderItemId) {
        return orderService.getOrderItem(userId, orderId, orderItemId);
    }
}