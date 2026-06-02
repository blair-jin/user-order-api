package com.example.userorder.application.auth.usecase;

import com.example.userorder.application.user.service.UserQueryService;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.dto.auth.AccessTokenResponse;
import com.example.userorder.dto.auth.RefreshTokenRequest;
import com.example.userorder.infrastructure.redis.RefreshTokenRedisRepository;
import com.example.userorder.security.jwt.JwtProvider;
import com.example.userorder.security.jwt.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final JwtProvider jwtProvider;
    private final UserQueryService userQueryService;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public AccessTokenResponse execute(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new InvalidLoginException("INVALID_REFRESH_TOKEN");
        }

        Long userId = jwtProvider.getUserId(refreshToken);

        if (!refreshTokenRedisRepository.matches(userId, refreshToken)) {
            throw new InvalidLoginException("EXPIRED_TOKEN");
        }

        JwtUserInfo userInfo = userQueryService.getJwtUserInfo(userId);
        String accessToken = jwtProvider.createAccessToken(userInfo);

        return new AccessTokenResponse(accessToken);
    }
}