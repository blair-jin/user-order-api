package com.example.userorder.application.order.facade;

import com.example.userorder.application.cart.reader.CartReader;
import com.example.userorder.application.cart.service.CartService;
import com.example.userorder.application.order.command.CreateOrderCommand;
import com.example.userorder.application.order.service.OrderService;
import com.example.userorder.domain.cart.Cart;
import com.example.userorder.domain.cart.CartItem;
import com.example.userorder.domain.common.vo.Quantity;
import com.example.userorder.domain.order.Order;
import com.example.userorder.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderService orderService;
    private final CartService cartService;
    private final CartReader cartReader;

    public OrderResponse execute(Long userId) {
        Cart cart = cartReader.getByUserId(userId);
        List<CartItem> items = cartReader.getAllByCartId(cart.getId());
        validateDuplicateProducts(items);

        List<CreateOrderCommand> command = toCreateOrderCommand(items);

        Order order = orderService.create(userId, command);
        return OrderResponse.from(order);
    }

    private List<CreateOrderCommand> toCreateOrderCommand(List<CartItem> items) {
        return items.stream()
                .map(item -> new CreateOrderCommand(
                        item.getProductId(), Quantity.of(item.getOrderQuantity())
                ))
                .toList();
    }

    private void validateDuplicateProducts(List<CartItem> items) {
        long distinctCount = items.stream()
                .map(CartItem::getProductId)
                .distinct()
                .count();

        if (distinctCount != items.size()) {
            throw new IllegalArgumentException();
        }
    }
}
