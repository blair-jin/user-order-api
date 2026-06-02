package com.example.userorder.infrastructure.redis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisKeyGenerator {
    private static final String LOGIN_FAIL_PREFIX = "auth:login-fail:";
    private static final String REFRESH_TOKEN_PREFIX = "auth:refresh:";

    public static String loginFailKey(String loginId) {
        return LOGIN_FAIL_PREFIX + loginId;
    }

    public static String refreshTokenKey(Long userId) {
        return REFRESH_TOKEN_PREFIX + userId;
    }
}