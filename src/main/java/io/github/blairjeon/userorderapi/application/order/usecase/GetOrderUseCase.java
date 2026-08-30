package io.github.blairjeon.userorderapi.application.order.usecase;

import io.github.blairjeon.userorderapi.application.order.reader.OrderReader;
import io.github.blairjeon.userorderapi.application.order.service.OrderQueryService;
import io.github.blairjeon.userorderapi.domain.order.Order;
import io.github.blairjeon.userorderapi.dto.order.OrderItemResponse;
import io.github.blairjeon.userorderapi.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrderUseCase {
    private final OrderReader orderReader;
    private final OrderQueryService orderQueryService;

    public List<OrderItemResponse> execute(Long userId, Long orderId){
        return orderQueryService.get(userId, orderId);
    }
}