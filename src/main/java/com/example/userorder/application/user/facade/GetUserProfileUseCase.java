package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.service.UserService;
import com.example.userorder.dto.user.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserProfileUseCase {
    private final UserService userService;

    public UserProfileResponse execute(Long userId) {
        return userService.getProfile(userId);
    }
}