package com.example.userorder.application.product.service;

import com.example.userorder.application.product.ProductValues;
import com.example.userorder.application.product.reader.ProductReader;
import com.example.userorder.domain.product.Product;
import com.example.userorder.dto.product.ProductResponse;
import com.example.userorder.dto.product.SearchProductCondition;
import com.example.userorder.repository.product.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductReader productReader;

    public ProductService(ProductRepository productRepository, ProductReader productReader) {
        this.productRepository = productRepository;
        this.productReader = productReader;
    }

    @Transactional
    public void create(ProductValues productValues) {
        Product product = Product.create(
                productValues.name(),
                productValues.stockQuantity(),
                productValues.unitPrice()
        );

        productRepository.save(product);
    }

    public Slice<ProductResponse> search(SearchProductCondition condition, Pageable pageable) {
        return productRepository.searchProducts(condition, pageable)
                .map(ProductResponse::from);
    }

    public ProductResponse get(Long productId) {
        Product product = productReader.getProductById(productId);
        return ProductResponse.from(product);
    }

    @Transactional
    public void update(Long productId, ProductValues productValues) {
        Product product = productReader.getProductById(productId);
        product.update(
                productValues.name(),
                productValues.stockQuantity(),
                productValues.unitPrice()
        );
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productReader.getProductById(productId);
        productRepository.delete(product);
    }
}