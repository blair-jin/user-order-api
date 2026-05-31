package com.example.userorder.application.user.facade;

import com.example.userorder.application.auth.service.LoginFailService;
import com.example.userorder.application.auth.service.RefreshTokenService;
import com.example.userorder.application.user.command.LoginCommand;
import com.example.userorder.application.user.service.UserQueryService;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.RawPassword;
import com.example.userorder.dto.auth.LoginRequest;
import com.example.userorder.dto.auth.LoginResponse;
import com.example.userorder.security.JwtProvider;
import com.example.userorder.security.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {
    private final UserQueryService userQueryService;
    private final LoginFailService loginFailService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;

    public LoginResponse execute(LoginRequest request) {
        LoginId loginId = LoginId.of(request.loginId());

        if (loginFailService.isBlocked(loginId.value())) {
            throw new InvalidLoginException("BLOCKED_ACCOUNT");
        }

        RawPassword rawPassword = RawPassword.of(request.password());
        LoginCommand command = new LoginCommand(loginId, rawPassword);

        try {
            JwtUserInfo userInfo = userQueryService.login(command);

            String accessToken = jwtProvider.createAccessToken(userInfo);
            String refreshToken = jwtProvider.createRefreshToken(userInfo.userId());
            refreshTokenService.save(userInfo.userId(), refreshToken);

            loginFailService.reset(loginId.value());
            return new LoginResponse(accessToken, refreshToken);
        } catch (InvalidLoginException e) {
            loginFailService.increase(loginId.value());
            throw e;
        }
    }
}