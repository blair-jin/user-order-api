package com.example.userorder.application.order.facade;

import com.example.userorder.application.order.service.OrderService;
import com.example.userorder.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetOrdersUseCase {
    private final OrderService orderService;

    public Slice<OrderResponse> execute(Long userId, Pageable pageable) {
        return orderService.searchOrders(userId, pageable);
    }
}
