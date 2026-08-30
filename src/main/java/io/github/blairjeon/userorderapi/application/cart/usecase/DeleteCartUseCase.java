package io.github.blairjeon.userorderapi.application.cart.usecase;

import io.github.blairjeon.userorderapi.application.cart.service.CartCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCartUseCase {
    private final CartCommandService cartCommandService;

    public void execute(Long userId){
        cartCommandService.delete(userId);
    }
}