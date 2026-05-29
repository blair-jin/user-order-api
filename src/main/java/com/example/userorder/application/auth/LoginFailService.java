package com.example.userorder.application.auth;

import com.example.userorder.domain.user.vo.LoginId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class LoginFailService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final Duration LOGIN_FAIL_TTL = Duration.ofMinutes(30);

    public void increaseFailCount(LoginId loginId) {
        String key = "login:failCount:" + loginId.value();
        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count == 1) {
            redisTemplate.expire(key, LOGIN_FAIL_TTL);
        }
    }

    public void resetFailCount(LoginId loginId) {

        String key = "login:failCount:" + loginId.value();
        redisTemplate.delete(key);
    }

    public boolean isBlocked(LoginId loginId) {
        String key = "login:failCount:" + loginId.value();

        String count = redisTemplate.opsForValue().get(key);

        if (count == null) {
            return false;
        }

        return Long.parseLong(count) >= 5;
    }
}