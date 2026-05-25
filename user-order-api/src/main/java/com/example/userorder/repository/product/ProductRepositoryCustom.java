package com.example.userorder.repository.product;

import com.example.userorder.domain.product.Product;
import com.example.userorder.dto.product.ProductSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    Slice<Product> searchProducts(ProductSearchCondition condition, Pageable pageable);
}
