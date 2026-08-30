package io.github.blairjeon.userorderapi.dto.order;

import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(
        @NotNull
        Long productId,

        @NotNull
        Integer quantity
) {
}