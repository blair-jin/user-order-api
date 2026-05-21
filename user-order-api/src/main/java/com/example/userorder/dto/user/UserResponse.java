package com.example.userorder.dto.user;

import com.example.userorder.domain.user.User;

public record UserResponse(
        String loginId,
        String role
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getLoginId(), user.getRole().name());
    }
}