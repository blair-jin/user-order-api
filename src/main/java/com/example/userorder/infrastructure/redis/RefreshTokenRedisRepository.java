package com.example.userorder.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean matches(Long userId, String refreshToken) {
        String key = RedisKeyGenerator.refreshTokenKey(userId);
        String value = redisTemplate.opsForValue().get(key);

        return value.matches(refreshToken);
    }

    public void save(Long userId, String refreshToken) {
        String key = RedisKeyGenerator.refreshTokenKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken);
    }

    public void remove(Long userId) {
        String key = RedisKeyGenerator.refreshTokenKey(userId);
        redisTemplate.delete(key);
    }
}