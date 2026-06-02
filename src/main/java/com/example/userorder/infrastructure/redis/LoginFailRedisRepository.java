package com.example.userorder.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class LoginFailRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisProperties redisProperties;

    public void increase(String loginId) {
        String key = RedisKeyGenerator.loginFailKey(loginId);
        Duration expiration = redisProperties.loginFail().ttl();

        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, expiration);
    }

    public void reset(String loginId) {
        String key = RedisKeyGenerator.loginFailKey(loginId);
        redisTemplate.delete(key);
    }

    public boolean isBlocked(String loginId) {
        String key = RedisKeyGenerator.loginFailKey(loginId);
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return false;
        }

        long count = Long.parseLong(value);

        return count >= redisProperties.loginFail().maxCount();
    }
}