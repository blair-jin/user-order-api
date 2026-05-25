package com.example.userorder.application.order.reader;

import com.example.userorder.common.exception.OrderItemNotFoundException;
import com.example.userorder.common.exception.OrderNotFoundException;
import com.example.userorder.domain.order.Order;
import com.example.userorder.domain.order.OrderItem;
import com.example.userorder.repository.order.OrderItemRepository;
import com.example.userorder.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReader {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public void validateOrderWithUserId(Long userId, Long orderId) {
        if (!orderRepository.existsByUserIdAndId(userId, orderId)) {
            throw new OrderNotFoundException();
        }
    }

    public Order getOrderByUserIdAndId(Long userId, Long orderId) {
        return orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    public OrderItem getItemByOrderIdAndIdAndUserId(Long orderId, Long orderItemId, Long userId) {
        return orderItemRepository.findByOrder_IdAndIdAndOrder_UserId(orderId, orderItemId, userId)
                .orElseThrow(OrderItemNotFoundException::new);
    }
}