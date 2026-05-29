package com.example.userorder.application.product.facade;

import com.example.userorder.application.product.service.ProductService;
import com.example.userorder.dto.product.ProductResponse;
import com.example.userorder.dto.product.ProductSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProductUseCase {
    private final ProductService productService;

    public Slice<ProductResponse> execute(ProductSearchCondition condition, Pageable pageable) {
        return productService.search(condition, pageable);
    }
}