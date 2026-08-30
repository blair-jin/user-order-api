package io.github.blairjeon.userorderapi.application.cart.usecase;

import io.github.blairjeon.userorderapi.application.cart.service.CartCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCartItemUseCase {
    private final CartCommandService cartCommandService;

    public void execute(Long userId, Long cartItemId){
        cartCommandService.deleteItem(userId, cartItemId);
    }
}