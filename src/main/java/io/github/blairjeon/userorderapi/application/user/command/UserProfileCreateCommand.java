package io.github.blairjeon.userorderapi.application.user.command;

import io.github.blairjeon.userorderapi.domain.user.vo.BirthDate;
import io.github.blairjeon.userorderapi.domain.user.vo.PhoneNumber;
import io.github.blairjeon.userorderapi.domain.user.vo.UserName;

public record UserProfileCreateCommand(
        UserName userName,
        String firstName,
        String lastName,
        BirthDate birthDate,
        PhoneNumber phoneNumber
) {
}