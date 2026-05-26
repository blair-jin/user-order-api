package com.example.userorder.application.order.facade;

import com.example.userorder.application.order.reader.OrderReader;
import com.example.userorder.application.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrderUseCase {
    private final OrderReader orderReader;
    private final OrderService orderService;

    public void execute(Long userId, Long orderId) {
        orderService.cancel(userId, orderId);
    }
}