package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.service.UserQueryService;
import com.example.userorder.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserUseCase {
    private final UserQueryService userQueryService;

    public UserResponse execute(Long userId) {
        return userQueryService.get(userId);
    }
}
