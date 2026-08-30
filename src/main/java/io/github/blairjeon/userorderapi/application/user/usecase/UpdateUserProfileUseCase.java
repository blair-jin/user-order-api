package io.github.blairjeon.userorderapi.application.user.usecase;

import io.github.blairjeon.userorderapi.application.user.command.UserCommandMapper;
import io.github.blairjeon.userorderapi.application.user.command.UserProfileUpdateCommand;
import io.github.blairjeon.userorderapi.application.user.service.UserCommandService;
import io.github.blairjeon.userorderapi.dto.user.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId, UserProfileUpdateRequest request){
        UserProfileUpdateCommand command = UserCommandMapper.toUpdateCommand(request);
        userCommandService.updateProfile(userId, command);
    }
}