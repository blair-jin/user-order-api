package com.example.userorder.dto.order;

import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record OrderSearchCondition(
        @PositiveOrZero
        Long minPrice,

        @PositiveOrZero
        Long maxPrice,

        LocalDate startDate,

        LocalDate endDate
) {
}