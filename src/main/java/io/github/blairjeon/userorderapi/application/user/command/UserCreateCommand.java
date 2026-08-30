package io.github.blairjeon.userorderapi.application.user.command;

import io.github.blairjeon.userorderapi.domain.user.vo.Email;
import io.github.blairjeon.userorderapi.domain.user.vo.EncodedPassword;
import io.github.blairjeon.userorderapi.domain.user.vo.LoginId;

public record UserCreateCommand(
        LoginId loginId,
        EncodedPassword password,
        Email email
) {
}