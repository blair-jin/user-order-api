package com.example.userorder.security;

import com.example.userorder.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long userId, String loginId, Role role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.expiration());

        return Jwts.builder()
                .signWith(getSigningKey())
                .issuedAt(now)
                .expiration(expiry)
                .subject(userId.toString())
                .claim("loginId", loginId)
                .claim("role", role.name())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public JwtUserInfo getUserInfo(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long userId = Long.valueOf(claims.getSubject());
        String loginId = claims.get("loginId", String.class);
        Role role = Role.valueOf(claims.get("role", String.class));

        return new JwtUserInfo(userId, loginId, role);
    }
}