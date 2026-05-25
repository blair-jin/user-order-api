package com.example.userorder.controller;

import com.example.userorder.application.order.facade.CreateOrderUseCase;
import com.example.userorder.application.order.facade.GetOrderItemUseCase;
import com.example.userorder.application.order.facade.GetOrderItemsUseCase;
import com.example.userorder.application.order.facade.GetOrdersUseCase;
import com.example.userorder.dto.order.OrderItemResponse;
import com.example.userorder.dto.order.OrderResponse;
import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrdersUseCase getOrdersUseCase;
    private final GetOrderItemsUseCase getOrderItemsUseCase;
    private final GetOrderItemUseCase getOrderItemUseCase;

    @PostMapping
    public void create(@AuthenticationPrincipal CustomUserPrincipal principal) {
        createOrderUseCase.execute(principal.userId());
    }

    @GetMapping
    public Slice<OrderResponse> searchOrders(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody OrderSearchCondition condition,
            Pageable pageable
    ) {
        return getOrdersUseCase.execute(principal.userId(), condition, pageable);
    }

    @GetMapping("/{orderId}/items")
    public Slice<OrderItemResponse> get(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId,
            Pageable pageable
    ) {
        return getOrderItemsUseCase.execute(principal.userId(), orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    public OrderItemResponse getOrderItem(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId, @PathVariable Long orderItemId
    ) {
        return getOrderItemUseCase.execute(principal.userId(), orderId, orderItemId);
    }
}