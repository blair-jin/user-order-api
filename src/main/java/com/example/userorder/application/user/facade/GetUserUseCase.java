package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.service.UserService;
import com.example.userorder.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserUseCase {
    private final UserService userService;

    public UserResponse execute(Long userId) {
        return userService.get(userId);
    }
}
