package com.example.userorder.repository.product;

import com.example.userorder.domain.product.Product;
import com.example.userorder.dto.product.SearchProductCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    Slice<Product> searchProducts(SearchProductCondition condition, Pageable pageable);
}
