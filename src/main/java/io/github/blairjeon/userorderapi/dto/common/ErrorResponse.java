package io.github.blairjeon.userorderapi.dto.common;

public record ErrorResponse(
        int status,
        String message
) {
}