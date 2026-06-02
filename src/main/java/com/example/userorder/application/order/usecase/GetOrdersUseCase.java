package com.example.userorder.application.order.usecase;

import com.example.userorder.application.order.service.OrderService;
import com.example.userorder.dto.order.OrderResponse;
import com.example.userorder.dto.order.OrderSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOrdersUseCase {
    private final OrderService orderService;

    public Slice<OrderResponse> execute(Long userId, OrderSearchCondition condition, Pageable pageable) {
        return orderService.searchOrders(userId, condition, pageable);
    }
}
