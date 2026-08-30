package io.github.blairjeon.userorderapi.application.auth.usecase;

import io.github.blairjeon.userorderapi.application.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutUseCase {
    private final RefreshTokenService refreshTokenService;

    public void execute(Long userId){
        refreshTokenService.delete(userId);
    }
}