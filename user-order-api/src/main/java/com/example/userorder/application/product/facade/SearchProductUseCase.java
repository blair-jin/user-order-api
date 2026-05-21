package com.example.userorder.application.product.facade;

import com.example.userorder.application.product.service.ProductService;
import com.example.userorder.dto.product.ProductResponse;
import com.example.userorder.dto.product.SearchProductCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProductUseCase {
    private final ProductService productService;

    public Slice<ProductResponse> execute(SearchProductCondition condition, Pageable pageable) {
        return productService.search(condition, pageable);
    }
}