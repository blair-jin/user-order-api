package com.example.userorder.controller;

import com.example.userorder.application.auth.usecase.LoginUseCase;
import com.example.userorder.application.auth.usecase.LogoutUseCase;
import com.example.userorder.application.auth.usecase.RefreshTokenUseCase;
import com.example.userorder.application.user.usecase.*;
import com.example.userorder.dto.user.UserCreateRequest;
import com.example.userorder.dto.user.UserProfileResponse;
import com.example.userorder.dto.user.UserProfileUpdateRequest;
import com.example.userorder.dto.user.UserResponse;
import com.example.userorder.security.principal.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final LoginUseCase loginUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final DeleteUserProfileUseCase deleteUserProfileUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Valid @RequestBody UserCreateRequest request
    ) {
        createUserUseCase.execute(request);
    }

    @GetMapping("/me")
    public UserResponse get(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return getUserUseCase.execute(principal.userId());
    }

    @GetMapping("/me/profile")
    public UserProfileResponse getProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return getUserProfileUseCase.execute(principal.userId());
    }

    @PatchMapping("/me/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody UserProfileUpdateRequest request
    ) {
        updateUserProfileUseCase.execute(principal.userId(), request);
    }


    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        deleteUserUseCase.execute(principal.userId());
    }

    @DeleteMapping("/me/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal CustomUserPrincipal principal) {
        deleteUserProfileUseCase.execute(principal.userId());
    }
}