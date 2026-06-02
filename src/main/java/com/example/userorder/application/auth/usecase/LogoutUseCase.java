package com.example.userorder.application.auth.usecase;

import com.example.userorder.infrastructure.redis.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void execute(Long userId) {
        refreshTokenRedisRepository.remove(userId);
    }
}