package com.example.userorder.security;

public record JwtUserInfo(
        Long userId,
        String loginId,
        String role
) {
}