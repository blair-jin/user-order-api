package io.github.blairjeon.userorderapi.application.product.command;

import io.github.blairjeon.userorderapi.domain.common.vo.Money;
import io.github.blairjeon.userorderapi.domain.product.vo.ProductName;
import io.github.blairjeon.userorderapi.domain.product.vo.StockQuantity;

public record ProductCreateCommand(
        ProductName productName,
        StockQuantity stockQuantity,
        Money unitPrice
) {
}