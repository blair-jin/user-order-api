package io.github.blairjeon.userorderapi.application.order.usecase;

import io.github.blairjeon.userorderapi.application.order.service.OrderCommandService;
import io.github.blairjeon.userorderapi.domain.order.vo.OrderQuantity;
import io.github.blairjeon.userorderapi.dto.order.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrderDirectUseCase {
    private final OrderCommandService orderCommandService;

    public void execute(Long userId, OrderCreateRequest request){
        OrderQuantity orderQuantity = OrderQuantity.of(request.quantity());

        orderCommandService.createDirect(
                userId,
                request.productId(),
                orderQuantity
        );
    }
}