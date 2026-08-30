package io.github.blairjeon.userorderapi.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserEmailUpdateRequest(
        @NotBlank
        String email
) {
}