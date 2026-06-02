package com.example.userorder.infrastructure.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "redis")
public record RedisProperties(
        LoginFail loginFail,
        EmailVerification emailVerification
) {
    public record LoginFail(
            Duration ttl,
            int maxCount
    ) {
    }

    public record EmailVerification(
            Duration ttl
    ) {
    }
}