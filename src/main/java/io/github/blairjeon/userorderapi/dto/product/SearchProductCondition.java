package io.github.blairjeon.userorderapi.dto.product;

import java.time.LocalDate;

public record SearchProductCondition(
        String keyword,
        Long minPrice,
        Long maxPrice,
        LocalDate startDate,
        LocalDate endDate
) {
}