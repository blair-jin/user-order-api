package com.example.userorder.controller;

import com.example.userorder.application.auth.usecase.LogoutUseCase;
import com.example.userorder.application.auth.usecase.RefreshTokenUseCase;
import com.example.userorder.application.auth.usecase.LoginUseCase;
import com.example.userorder.dto.auth.AccessTokenResponse;
import com.example.userorder.dto.auth.LoginRequest;
import com.example.userorder.dto.auth.LoginResponse;
import com.example.userorder.dto.auth.RefreshTokenRequest;
import com.example.userorder.security.principal.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return loginUseCase.execute(request);
    }

    @PostMapping("/refresh")
    public AccessTokenResponse refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return refreshTokenUseCase.execute(request);
    }

    @PostMapping("/logout")
    public void logout(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        logoutUseCase.execute(principal.userId());
    }
}