package com.example.userorder.dto.product;

import java.time.LocalDate;

public record ProductSearchCondition(
        String name,
        Long minPrice,
        Long maxPrice,
        LocalDate startDate,
        LocalDate endDate
) {
}