package com.example.userorder.application.user.facade;

import com.example.userorder.application.auth.LoginFailService;
import com.example.userorder.application.user.command.LoginCommand;
import com.example.userorder.application.user.service.UserQueryService;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.RawPassword;
import com.example.userorder.dto.user.UserLoginRequest;
import com.example.userorder.dto.user.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserUseCase {
    private final LoginFailService loginFailService;
    private final UserQueryService userQueryService;

    public UserLoginResponse execute(UserLoginRequest request) {
        LoginId loginId = LoginId.of(request.loginId());

        if (loginFailService.isBlocked(loginId)) {
            throw new InvalidLoginException("BLOCKED_ACCOUNT");
        }

        RawPassword rawPassword = RawPassword.of(request.password());
        LoginCommand command = new LoginCommand(loginId, rawPassword);

        try {
            String token = userQueryService.login(command);
            loginFailService.resetFailCount(loginId);
            return new UserLoginResponse(token);
        } catch (InvalidLoginException e) {
            loginFailService.increaseFailCount(loginId);
            throw e;
        }
    }
}