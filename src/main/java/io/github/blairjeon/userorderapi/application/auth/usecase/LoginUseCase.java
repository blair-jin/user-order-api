package io.github.blairjeon.userorderapi.application.auth.usecase;

import io.github.blairjeon.userorderapi.application.auth.service.AuthenticationService;
import io.github.blairjeon.userorderapi.application.auth.service.RefreshTokenService;
import io.github.blairjeon.userorderapi.domain.user.User;
import io.github.blairjeon.userorderapi.domain.user.vo.LoginId;
import io.github.blairjeon.userorderapi.domain.user.vo.Password;
import io.github.blairjeon.userorderapi.dto.auth.LoginRequest;
import io.github.blairjeon.userorderapi.dto.auth.LoginResponse;
import io.github.blairjeon.userorderapi.infrastructure.jwt.JwtProvider;
import io.github.blairjeon.userorderapi.infrastructure.jwt.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {
    private final JwtProvider jwtProvider;
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse execute(LoginRequest request){
        LoginId loginId = LoginId.of(request.loginId());
        Password password = Password.of(request.password());

        User user = authenticationService.authenticate(loginId, password);
        JwtUserInfo userInfo = new JwtUserInfo(user.getId(), user.getLoginId(), user.getRole());

        String accessToken = jwtProvider.createAccessToken(userInfo);

        String refreshToken = jwtProvider.createRefreshToken(user.getId());
        refreshTokenService.save(user.getId(), refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }
}