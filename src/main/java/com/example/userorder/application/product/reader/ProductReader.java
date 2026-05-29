package com.example.userorder.application.product.reader;

import com.example.userorder.common.exception.ProductNotFoundException;
import com.example.userorder.domain.product.Product;
import com.example.userorder.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductReader {
    private final ProductRepository productRepository;

    public List<Product> getProductsByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> getProductsByIdsWithPessimisticLock(List<Long> productIds) {
        return productRepository.findAllByIdWithPessimisticLock(productIds).stream()
                .distinct()
                .sorted(Comparator.comparing(Product::getId))
                .toList();
    }
}