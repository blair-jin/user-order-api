package io.github.blairjeon.userorderapi.application.auth.service;

import io.github.blairjeon.userorderapi.infrastructure.jwt.JwtProperties;
import io.github.blairjeon.userorderapi.infrastructure.redis.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class BlacklistTokenService {
    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    public void add(String accessToken){
        String key = RedisKeys.accessTokenBlacklist(accessToken);
        Duration ttl = jwtProperties.accessTokenExpiration();

        redisTemplate.opsForValue().set(key, accessToken, ttl);
    }

    public Boolean exists(String accessToken){
        String key = RedisKeys.accessTokenBlacklist(accessToken);
        Boolean hasToken = redisTemplate.hasKey(key);

        return hasToken;
    }
}