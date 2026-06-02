package com.example.userorder.application.cart.usecase;

import com.example.userorder.application.cart.service.CartService;
import com.example.userorder.dto.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetItemUseCase {
    private final CartService cartService;

    public CartItemResponse execute(Long userId, Long cartItemId) {
        return cartService.getItemById(userId, cartItemId);
    }
}
