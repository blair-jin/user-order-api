package io.github.blairjeon.userorderapi.application.user.usecase;

import io.github.blairjeon.userorderapi.application.user.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId){
        userCommandService.delete(userId);
    }
}