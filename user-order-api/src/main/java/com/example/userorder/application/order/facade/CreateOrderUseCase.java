package com.example.userorder.application.order.facade;

import com.example.userorder.application.cart.service.CartService;
import com.example.userorder.application.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderService orderService;
    private final CartService cartService;

    @Transactional
    public void execute(Long userId) {
        orderService.create(userId);
        cartService.cleanCart(userId);
    }
}
