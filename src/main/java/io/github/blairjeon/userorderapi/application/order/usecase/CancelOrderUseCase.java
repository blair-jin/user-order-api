package io.github.blairjeon.userorderapi.application.order.usecase;

import io.github.blairjeon.userorderapi.application.order.service.OrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrderUseCase {
    private final OrderCommandService orderCommandService;

    public void execute(Long userId, Long orderId){
        orderCommandService.cancel(userId, orderId);
    }
}