package com.example.userorder.security;

import com.example.userorder.domain.user.Role;

public record JwtUserInfo(
        Long userId,
        String loginId,
        Role role
) {
}