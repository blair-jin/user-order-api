package com.example.userorder.controller;

import com.example.userorder.application.product.usecase.*;
import com.example.userorder.dto.product.ProductCreateRequest;
import com.example.userorder.dto.product.ProductResponse;
import com.example.userorder.dto.product.ProductUpdateRequest;
import com.example.userorder.dto.product.ProductSearchCondition;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final SearchProductUseCase searchProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final CreateProductUseCase createProductUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void create(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        createProductUseCase.execute(request);
    }

    @GetMapping
    public Slice<ProductResponse> searchProducts(
            @ModelAttribute ProductSearchCondition condition,
            Pageable pageable
    ) {
        return searchProductUseCase.execute(condition, pageable);
    }

    @GetMapping("/{productId}")
    public ProductResponse get(
            @PathVariable Long productId
    ) {
        return getProductUseCase.execute(productId);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        updateProductUseCase.execute(productId, request);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long productId
    ) {
        deleteProductUseCase.execute(productId);
    }
}