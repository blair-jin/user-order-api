package io.github.blairjeon.userorderapi.application.product.usecase;

import io.github.blairjeon.userorderapi.application.product.service.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductUseCase {
    private final ProductCommandService productCommandService;

    public void execute(Long userId, Long productId){
        productCommandService.delete(userId, productId);
    }
}