package io.github.blairjeon.userorderapi.application.product.command;

import io.github.blairjeon.userorderapi.domain.common.vo.Money;
import io.github.blairjeon.userorderapi.domain.product.vo.ProductName;
import io.github.blairjeon.userorderapi.domain.product.vo.StockQuantity;
import io.github.blairjeon.userorderapi.dto.product.ProductCreateRequest;
import io.github.blairjeon.userorderapi.dto.product.ProductUpdateRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductCommandMapper {
    public static ProductCreateCommand toCommand(ProductCreateRequest request){
        return new ProductCreateCommand(
                ProductName.of(request.productName()),
                StockQuantity.of(request.stockQuantity()),
                Money.of(request.unitPrice())
        );
    }

    public static ProductUpdateCommand toCommand(ProductUpdateRequest request){
        return new ProductUpdateCommand(
                request.productName() != null
                        ? ProductName.of(request.productName())
                        : null,
                request.stockQuantity() != null
                        ? StockQuantity.of(request.stockQuantity())
                        : null,
                request.unitPrice() != null
                        ? Money.of(request.unitPrice())
                        : null,
                request.productStatus() != null
                        ? request.productStatus()
                        :null
        );
    }
}