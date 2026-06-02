package com.example.userorder.application.product.usecase;

import com.example.userorder.application.product.model.ProductValues;
import com.example.userorder.application.product.service.ProductService;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.dto.product.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductUseCase {
    private final ProductService productService;

    public void execute(Long productId, ProductUpdateRequest request) {
        Quantity stockQuantity = Quantity.of(request.stockQuantity());
        Money unitPrice = Money.of(request.unitPrice());

        ProductValues productValues = new ProductValues(request.name(), stockQuantity, unitPrice);
        productService.update(productId, productValues);
    }
}
