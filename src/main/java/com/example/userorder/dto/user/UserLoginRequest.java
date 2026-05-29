package com.example.userorder.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank(message = "Login ID is required")
        String loginId,

        @NotBlank(message = "Password is required")
        String password
) {
}