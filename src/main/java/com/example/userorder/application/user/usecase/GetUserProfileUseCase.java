package com.example.userorder.application.user.usecase;

import com.example.userorder.application.user.service.UserQueryService;
import com.example.userorder.dto.user.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserProfileUseCase {
    private final UserQueryService userQueryService;

    public UserProfileResponse execute(Long userId) {
        return userQueryService.getProfile(userId);
    }
}