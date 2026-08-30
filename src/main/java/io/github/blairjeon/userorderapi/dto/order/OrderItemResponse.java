package io.github.blairjeon.userorderapi.dto.order;

import io.github.blairjeon.userorderapi.domain.order.OrderItem;
import io.github.blairjeon.userorderapi.domain.product.Product;

public record OrderItemResponse(
        Long productId,
        String productName,
        int orderQuantity,
        long unitPrice,
        long totalPrice
) {
    public static OrderItemResponse from(OrderItem item){
        return new OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getOrderQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }
}