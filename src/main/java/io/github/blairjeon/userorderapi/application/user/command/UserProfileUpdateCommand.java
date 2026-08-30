package io.github.blairjeon.userorderapi.application.user.command;

import io.github.blairjeon.userorderapi.domain.user.vo.BirthDate;
import io.github.blairjeon.userorderapi.domain.user.vo.PhoneNumber;
import io.github.blairjeon.userorderapi.domain.user.vo.UserName;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;

public record UserProfileUpdateCommand(
        JsonNullable<UserName> userName,
        JsonNullable<String> firstName,
        JsonNullable<String> lastName,
        JsonNullable<BirthDate> birthDate,
        JsonNullable<PhoneNumber> phoneNumber
) {
}