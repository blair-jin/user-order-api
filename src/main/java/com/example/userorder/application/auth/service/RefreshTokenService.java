package com.example.userorder.application.auth.service;

import com.example.userorder.security.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtProperties jwtProperties;

    private final RedisTemplate<String, String> redisTemplate;

    public void save(Long userId, String refreshToken) {
        String key = getKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(jwtProperties.refreshExpiration()));
    }

    public boolean matches(Long userId, String refreshToken) {
        String key = getKey(userId);
        String token = redisTemplate.opsForValue().get(key);

        return Objects.equals(refreshToken, token);
    }

    public void delete(Long userId) {
        String key = getKey(userId);
        redisTemplate.delete(key);
    }

    private String getKey(Long userId) {
        return "refresh:token:" + userId;
    }
}