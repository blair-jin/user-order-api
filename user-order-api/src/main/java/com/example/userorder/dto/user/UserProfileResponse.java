package com.example.userorder.dto.user;

import com.example.userorder.domain.user.UserProfile;

import java.time.LocalDate;

public record UserProfileResponse(
        Long userId,
        String name,
        LocalDate birthDate,
        String email
) {
    public static UserProfileResponse from(UserProfile profile) {
        return new UserProfileResponse(
                profile.getId(),
                profile.getName(),
                profile.getBirthDate(),
                profile.getEmail()
        );
    }
}