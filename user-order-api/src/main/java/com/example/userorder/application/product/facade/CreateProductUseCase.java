package com.example.userorder.application.product.facade;

import com.example.userorder.application.product.ProductValues;
import com.example.userorder.application.product.service.ProductService;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.dto.product.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductService productService;

    public void execute(ProductCreateRequest request) {
        Quantity stockQuantity = Quantity.of(request.stockQuantity());
        Money unitPrice = Money.of(request.unitPrice());

        ProductValues productValues =
                new ProductValues(request.name(), stockQuantity, unitPrice);
        productService.create(productValues);
    }
}