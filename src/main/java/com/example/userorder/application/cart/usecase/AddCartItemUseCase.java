package com.example.userorder.application.cart.usecase;

import com.example.userorder.application.cart.service.CartService;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.dto.cart.CartItemAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddCartItemUseCase {
    private final CartService cartService;

    public void execute(Long userId, CartItemAddRequest request) {
        Quantity orderQuantity = Quantity.of(request.orderQuantity());
        Long productId = request.productId();

        cartService.addItem(userId, productId, orderQuantity);
    }
}
