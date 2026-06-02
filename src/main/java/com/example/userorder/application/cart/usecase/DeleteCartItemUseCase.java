package com.example.userorder.application.cart.usecase;

import com.example.userorder.application.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCartItemUseCase {
    private final CartService cartService;

    public void execute(Long userId, Long cartItemId) {
        cartService.deleteItem(userId, cartItemId);
    }
}