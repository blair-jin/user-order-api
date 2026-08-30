package io.github.blairjeon.userorderapi.application.user.usecase;

import io.github.blairjeon.userorderapi.application.user.service.UserCommandService;
import io.github.blairjeon.userorderapi.domain.user.vo.Email;
import io.github.blairjeon.userorderapi.dto.user.UserEmailUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserEmailUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId, UserEmailUpdateRequest request){
        Email email = Email.of(request.email());
        userCommandService.update(userId, email);
    }
}