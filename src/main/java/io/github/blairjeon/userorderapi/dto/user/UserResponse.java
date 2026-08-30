package io.github.blairjeon.userorderapi.dto.user;

import io.github.blairjeon.userorderapi.domain.user.User;
import io.github.blairjeon.userorderapi.domain.user.UserRole;

public record UserResponse(
        String loginId,
        String email,
        UserRole role
) {
    public static UserResponse from(User user){
        return new UserResponse(user.getLoginId(), user.getEmail(), user.getRole());
    }
}