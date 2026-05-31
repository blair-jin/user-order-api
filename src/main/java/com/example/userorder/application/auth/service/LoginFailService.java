package com.example.userorder.application.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginFailService {
    private static final Duration LOGIN_FAIL_TTL = Duration.ofMinutes(30);

    private final RedisTemplate<String, String> redisTemplate;

    public void increase(String loginId) {
        String key = getKey(loginId);

        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, LOGIN_FAIL_TTL);
    }

    public void reset(String loginId) {
        String key = getKey(loginId);
        redisTemplate.delete(key);
    }

    public boolean isBlocked(String loginId) {
        String key = getKey(loginId);
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return false;
        }

        long count = Long.parseLong(value);
        return count >= 5;
    }

    private String getKey(String loginId) {
        return "login:fail:" + loginId;
    }
}