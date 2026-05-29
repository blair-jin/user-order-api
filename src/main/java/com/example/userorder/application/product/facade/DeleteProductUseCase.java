package com.example.userorder.application.product.facade;

import com.example.userorder.application.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductUseCase {
    private final ProductService productService;

    public void execute(Long productId) {
        productService.delete(productId);
    }
}