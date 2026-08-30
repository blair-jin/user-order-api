package io.github.blairjeon.userorderapi.application.order.usecase;

import io.github.blairjeon.userorderapi.application.order.service.OrderQueryService;
import io.github.blairjeon.userorderapi.dto.order.OrderResponse;
import io.github.blairjeon.userorderapi.dto.order.SearchOrderCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchOrdersUseCase {
    private final OrderQueryService orderQueryService;

    public Slice<OrderResponse> execute(Long userId, SearchOrderCondition condition, Pageable pageable){
        return orderQueryService.search(userId, condition, pageable);
    }
}