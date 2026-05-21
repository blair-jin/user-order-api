package com.example.userorder.application.order.facade;

import com.example.userorder.application.order.service.OrderService;
import com.example.userorder.dto.order.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOrderItemUseCase {
    private final OrderService orderService;

    public Slice<OrderItemResponse> execute(Long userId, Long orderId, Pageable pageable) {
        return orderService.searchOrderItems(userId, orderId, pageable);
    }
}