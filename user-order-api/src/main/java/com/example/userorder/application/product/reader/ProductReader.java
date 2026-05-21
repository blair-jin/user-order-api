package com.example.userorder.application.product.reader;

import com.example.userorder.common.exception.ProductNotFoundException;
import com.example.userorder.domain.product.Product;
import com.example.userorder.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductReader {
    private final ProductRepository productRepository;

    public void existsById(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }
    }

    public List<Product> getProductsByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}