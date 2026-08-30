package io.github.blairjeon.userorderapi.application.order.command;

import io.github.blairjeon.userorderapi.domain.common.vo.Money;
import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;
import io.github.blairjeon.userorderapi.domain.product.vo.ProductName;

public record OrderItemCommand(
        Long productId,
        ProductName productName,
        OrderQuantity orderQuantity,
        Money unitPrice
) {
}