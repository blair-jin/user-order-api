package com.example.userorder.dto.auth;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}