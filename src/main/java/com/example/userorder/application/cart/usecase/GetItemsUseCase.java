package com.example.userorder.application.cart.usecase;

import com.example.userorder.application.cart.service.CartService;
import com.example.userorder.dto.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetItemsUseCase {
    private final CartService cartService;

    public Slice<CartItemResponse> execute(Long userId, Pageable pageable) {
        return cartService.getItems(userId, pageable);
    }
}