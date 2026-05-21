package com.example.userorder.dto.product;

import java.time.LocalDate;

public record SearchProductCondition(
        String name,
        Long minPrice,
        Long maxPrice,
        LocalDate startDate,
        LocalDate endDate
) {
}