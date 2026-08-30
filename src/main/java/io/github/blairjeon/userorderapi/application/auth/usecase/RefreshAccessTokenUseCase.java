package io.github.blairjeon.userorderapi.application.auth.usecase;

import io.github.blairjeon.userorderapi.application.auth.service.RefreshTokenService;
import io.github.blairjeon.userorderapi.application.user.reader.UserReader;
import io.github.blairjeon.userorderapi.domain.user.User;
import io.github.blairjeon.userorderapi.dto.auth.AccessTokenResponse;
import io.github.blairjeon.userorderapi.dto.auth.TokenRefreshRequest;
import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidTokenException;
import io.github.blairjeon.userorderapi.infrastructure.jwt.JwtProvider;
import io.github.blairjeon.userorderapi.infrastructure.jwt.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshAccessTokenUseCase {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserReader userReader;

    public AccessTokenResponse execute(TokenRefreshRequest request){
        String refreshToken = request.refreshToken();

        if(!jwtProvider.validateToken(refreshToken)){
            throw new InvalidTokenException();
        }

        Long userId = jwtProvider.getUserId(refreshToken);
        refreshTokenService.validateRefreshToken(userId, refreshToken);

        User user = userReader.getUserById(userId);

        JwtUserInfo userInfo = JwtUserInfo.from(user);
        String accessToken = jwtProvider.createAccessToken(userInfo);

        return new AccessTokenResponse(accessToken);
    }
}