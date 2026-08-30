package io.github.blairjeon.userorderapi.application.user.usecase;

import io.github.blairjeon.userorderapi.application.user.reader.UserReader;
import io.github.blairjeon.userorderapi.domain.user.User;
import io.github.blairjeon.userorderapi.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserUseCase {
    private final UserReader userReader;

    public UserResponse execute(Long userId){
        User user = userReader.getUserById(userId);
        return UserResponse.from(user);
    }
}