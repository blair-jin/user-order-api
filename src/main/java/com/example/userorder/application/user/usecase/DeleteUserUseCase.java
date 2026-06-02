package com.example.userorder.application.user.usecase;

import com.example.userorder.application.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId) {
        userCommandService.delete(userId);
    }
}