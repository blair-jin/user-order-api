package io.github.blairjeon.userorderapi.repository.product;

import io.github.blairjeon.userorderapi.domain.product.Product;
import io.github.blairjeon.userorderapi.dto.product.SearchProductCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    public Slice<Product> search(SearchProductCondition condition, Pageable pageable);
}