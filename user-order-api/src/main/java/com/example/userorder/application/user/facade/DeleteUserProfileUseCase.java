package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserProfileUseCase {
    private final UserService userService;

    public void execute(Long userId) {
        userService.deleteProfile(userId);
    }
}