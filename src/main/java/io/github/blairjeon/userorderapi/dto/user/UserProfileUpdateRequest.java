package io.github.blairjeon.userorderapi.dto.user;

import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

public record UserProfileUpdateRequest(
        JsonNullable<String> userName,
        JsonNullable<String> firstName,
        JsonNullable<String> lastName,
        JsonNullable<LocalDate> birthDate,
        JsonNullable<String> phoneNumber
) {
}