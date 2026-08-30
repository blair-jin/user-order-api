package io.github.blairjeon.userorderapi.application.product.usecase;

import io.github.blairjeon.userorderapi.application.product.service.ProductQueryService;
import io.github.blairjeon.userorderapi.dto.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductUseCase {
    private final ProductQueryService productQueryService;

    public ProductResponse execute(Long productId){
        return productQueryService.get(productId);
    }
}