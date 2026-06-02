package com.example.userorder.application.product.usecase;

import com.example.userorder.application.product.service.ProductService;
import com.example.userorder.dto.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductUseCase {
    private final ProductService productService;

    public ProductResponse execute(Long productId) {
        return productService.get(productId);
    }
}
