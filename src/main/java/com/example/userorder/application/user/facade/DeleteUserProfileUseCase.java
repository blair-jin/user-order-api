package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserProfileUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId) {
        userCommandService.deleteProfile(userId);
    }
}