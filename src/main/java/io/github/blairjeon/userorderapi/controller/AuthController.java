package io.github.blairjeon.userorderapi.controller;

import io.github.blairjeon.userorderapi.application.auth.usecase.LoginUseCase;
import io.github.blairjeon.userorderapi.application.auth.usecase.LogoutUseCase;
import io.github.blairjeon.userorderapi.application.auth.usecase.RefreshAccessTokenUseCase;
import io.github.blairjeon.userorderapi.dto.auth.AccessTokenResponse;
import io.github.blairjeon.userorderapi.dto.auth.LoginRequest;
import io.github.blairjeon.userorderapi.dto.auth.LoginResponse;
import io.github.blairjeon.userorderapi.dto.auth.TokenRefreshRequest;
import io.github.blairjeon.userorderapi.infrastructure.jwt.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
        return loginUseCase.execute(request);
    }

    @PostMapping("/refresh")
    public AccessTokenResponse refresh(@Valid @RequestBody TokenRefreshRequest request){
        return refreshAccessTokenUseCase.execute(request);
    }

    @PostMapping("/logout")
    public void logout(@AuthenticationPrincipal CustomUserPrincipal principal){
        logoutUseCase.execute(principal.userId());
    }
}