package io.github.blairjeon.userorderapi.application.cart.usecase;

import io.github.blairjeon.userorderapi.application.cart.service.CartCommandService;
import io.github.blairjeon.userorderapi.application.product.reader.ProductReader;
import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;
import io.github.blairjeon.userorderapi.domain.product.Product;
import io.github.blairjeon.userorderapi.dto.cart.CartItemUpdateRequest;
import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCartItemUseCase {
    private final CartCommandService cartCommandService;
    private final ProductReader productReader;

    public void execute(Long userId, Long cartItemId, CartItemUpdateRequest request){
        OrderQuantity quantity = OrderQuantity.of(request.quantity());
        cartCommandService.updateCartItem(userId, cartItemId, quantity);
    }
}