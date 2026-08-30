package io.github.blairjeon.userorderapi.application.order.command;

import io.github.blairjeon.userorderapi.domain.cart.CartItem;
import io.github.blairjeon.userorderapi.domain.common.vo.Money;
import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;
import io.github.blairjeon.userorderapi.domain.product.Product;
import io.github.blairjeon.userorderapi.domain.product.vo.ProductName;

public final class OrderCommandMapper {
    public static OrderItemCommand toCommand(Product product, CartItem cartItem){
        return new OrderItemCommand(
                product.getId(),
                ProductName.of(product.getProductName()),
                OrderQuantity.of(cartItem.getOrderQuantity()),
                Money.of(product.getUnitPrice())
        );
    }

    public static OrderItemCommand toCommand(Product product, OrderQuantity orderQuantity){
        return new OrderItemCommand(
                product.getId(),
                ProductName.of(product.getProductName()),
                orderQuantity,
                Money.of(product.getUnitPrice())
        );
    }
}