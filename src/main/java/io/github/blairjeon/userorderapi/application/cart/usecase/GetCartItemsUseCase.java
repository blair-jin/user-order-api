package io.github.blairjeon.userorderapi.application.cart.usecase;

import io.github.blairjeon.userorderapi.application.cart.service.CartQueryService;
import io.github.blairjeon.userorderapi.dto.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCartItemsUseCase {
    private final CartQueryService cartQueryService;

    public List<CartItemResponse> execute(Long userId) {
        return cartQueryService.get(userId);
    }
}