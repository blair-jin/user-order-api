package com.example.userorder.application.cart.facade;

import com.example.userorder.application.cart.service.CartService;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.dto.cart.CartItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCartItemUseCase {
    private final CartService cartService;

    public void execute(Long userId, Long cartItemId, CartItemUpdateRequest request) {
        Quantity orderQuantity = Quantity.of(request.orderQuantity());

        cartService.updateItem(userId, cartItemId, orderQuantity);
    }
}