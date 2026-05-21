package com.example.userorder.controller;

import com.example.userorder.application.user.facade.*;
import com.example.userorder.dto.user.*;
import com.example.userorder.security.CustomUserPrincipal;
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
    private final LoginUserUseCase loginUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final DeleteUserProfileUseCase deleteUserProfileUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @Valid @RequestBody UserCreateRequest request
    ) {
        createUserUseCase.execute(request);
    }

    @PostMapping("/login")
    public UserLoginResponse login(
            @Valid @RequestBody UserLoginRequest request
    ) {
        return loginUserUseCase.execute(request);
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