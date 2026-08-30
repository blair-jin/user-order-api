package io.github.blairjeon.userorderapi.dto.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}