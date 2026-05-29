package com.example.userorder.dto.user;

import java.time.LocalDate;

public record UserProfileUpdateRequest(
        String name,
        LocalDate birthDate,
        String email
) {
}