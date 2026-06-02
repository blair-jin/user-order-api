package com.example.userorder.application.user.usecase;

import com.example.userorder.application.user.command.CreateUserCommand;
import com.example.userorder.application.user.model.UserProfileValues;
import com.example.userorder.application.user.reader.UserReader;
import com.example.userorder.application.user.service.UserCommandService;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.Password;
import com.example.userorder.domain.user.vo.RawPassword;
import com.example.userorder.dto.user.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;
    private final UserCommandService userCommandService;

    public void execute(UserCreateRequest request) {
        LoginId loginId = LoginId.of(request.loginId());
        userReader.validateLoginIdAvailable(loginId);

        Password password = encodePassword(request.password());

        UserProfileValues profileValues =
                UserProfileValues.of(request.name(), request.birthDate(), request.email());

        CreateUserCommand command = CreateUserCommand.of(loginId, password, profileValues);

        userCommandService.create(command);
    }

    private Password encodePassword(String password) {
        RawPassword rawPassword = RawPassword.of(password);
        String encodedPassword = passwordEncoder.encode(rawPassword.value());
        return Password.of(encodedPassword);
    }
}