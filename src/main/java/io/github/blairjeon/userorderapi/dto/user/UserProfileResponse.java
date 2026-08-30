package io.github.blairjeon.userorderapi.dto.user;

import io.github.blairjeon.userorderapi.domain.user.UserProfile;

import java.time.LocalDate;

public record UserProfileResponse(
        String userName,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phoneNumber
) {
    public static UserProfileResponse from(UserProfile profile){
        return new UserProfileResponse(
                profile.getUserName(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthDate(),
                profile.getPhoneNumber()
        );
    }
}