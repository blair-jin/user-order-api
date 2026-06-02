package com.example.userorder.application.auth.usecase;

import com.example.userorder.application.user.command.LoginCommand;
import com.example.userorder.application.user.service.UserQueryService;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.RawPassword;
import com.example.userorder.dto.auth.LoginRequest;
import com.example.userorder.dto.auth.LoginResponse;
import com.example.userorder.infrastructure.redis.LoginFailRedisRepository;
import com.example.userorder.infrastructure.redis.RefreshTokenRedisRepository;
import com.example.userorder.security.jwt.JwtProvider;
import com.example.userorder.security.jwt.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {
    private final UserQueryService userQueryService;
    private final JwtProvider jwtProvider;
    private final LoginFailRedisRepository loginFailRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public LoginResponse execute(LoginRequest request) {
        LoginId loginId = LoginId.of(request.loginId());

        if (loginFailRedisRepository.isBlocked(loginId.value())) {
            throw new InvalidLoginException("BLOCKED_ACCOUNT");
        }

        RawPassword rawPassword = RawPassword.of(request.password());
        LoginCommand command = new LoginCommand(loginId, rawPassword);

        try {
            JwtUserInfo userInfo = userQueryService.login(command);

            String accessToken = jwtProvider.createAccessToken(userInfo);
            String refreshToken = jwtProvider.createRefreshToken(userInfo.userId());
            refreshTokenRedisRepository.save(userInfo.userId(), refreshToken);

            loginFailRedisRepository.reset(loginId.value());
            return new LoginResponse(accessToken, refreshToken);
        } catch (InvalidLoginException e) {
            loginFailRedisRepository.increase(loginId.value());
            throw e;
        }
    }
}