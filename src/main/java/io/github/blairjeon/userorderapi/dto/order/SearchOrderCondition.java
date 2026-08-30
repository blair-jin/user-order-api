package io.github.blairjeon.userorderapi.dto.order;

import io.github.blairjeon.userorderapi.domain.order.OrderStatus;

import java.time.LocalDate;

public record SearchOrderCondition(
        Long minPrice,
        Long maxPrice,
        OrderStatus orderStatus,
        LocalDate startDate,
        LocalDate endDate
) {
}