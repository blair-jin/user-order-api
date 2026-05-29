package com.example.userorder.application.user.command;

import com.example.userorder.application.user.model.UserProfileValues;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.Password;

import java.util.Objects;

public record CreateUserCommand(
        LoginId loginId,
        Password encodedPassword,
        UserProfileValues profileValues
) {
    public static CreateUserCommand of(
            LoginId loginId,
            Password encodedPassword,
            UserProfileValues profileValues
    ) {
        return new CreateUserCommand(
                Objects.requireNonNull(loginId),
                Objects.requireNonNull(encodedPassword),
                Objects.requireNonNull(profileValues)
        );
    }
}