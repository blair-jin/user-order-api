package com.example.userorder.application.user.command;

import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.RawPassword;

public record LoginCommand(
        LoginId loginId,
        RawPassword rawPassword
) {
}