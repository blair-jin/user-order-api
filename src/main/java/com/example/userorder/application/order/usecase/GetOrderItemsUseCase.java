package com.example.userorder.application.order.usecase;

import com.example.userorder.application.order.service.OrderService;
import com.example.userorder.dto.order.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOrderItemsUseCase {
    private final OrderService orderService;

    public Slice<OrderItemResponse> execute(Long userId, Long orderId, Pageable pageable) {
        return orderService.searchOrderItems(userId, orderId, pageable);
    }
}