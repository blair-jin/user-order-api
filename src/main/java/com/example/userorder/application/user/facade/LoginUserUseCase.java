package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.service.UserService;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.RawPassword;
import com.example.userorder.dto.user.UserLoginRequest;
import com.example.userorder.dto.user.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserUseCase {
    private final UserService userService;

    public UserLoginResponse execute(UserLoginRequest request) {
        LoginId loginId = LoginId.of(request.loginId());
        RawPassword password = RawPassword.of(request.password());

        return userService.login(loginId, password);
    }
}