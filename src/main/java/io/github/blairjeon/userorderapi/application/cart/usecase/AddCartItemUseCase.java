package io.github.blairjeon.userorderapi.application.cart.usecase;

import io.github.blairjeon.userorderapi.application.cart.command.CartItemCreateCommand;
import io.github.blairjeon.userorderapi.application.cart.service.CartCommandService;
import io.github.blairjeon.userorderapi.application.product.reader.ProductReader;
import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;
import io.github.blairjeon.userorderapi.dto.cart.CartItemAddRequest;
import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddCartItemUseCase {
    private final CartCommandService cartCommandService;
    private final ProductReader productReader;

    public void execute(Long userId, CartItemAddRequest request){
        Integer stockQuantity = productReader.getStockById(request.productId());

        if(request.quantity() > stockQuantity){
            throw new InsufficientStockException();
        }

        CartItemCreateCommand command =
                new CartItemCreateCommand(
                        request.productId(),
                        OrderQuantity.of(request.quantity())
                );

        cartCommandService.addCartItem(userId, command);
    }
}