package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.model.UserProfileValues;
import com.example.userorder.application.user.reader.UserReader;
import com.example.userorder.application.user.service.UserService;
import com.example.userorder.domain.user.vo.*;
import com.example.userorder.dto.user.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public void execute(UserCreateRequest request) {
        LoginId loginId = LoginId.of(request.loginId());
        userReader.validateLoginIdAvailable(loginId);

        Password password = encodePassword(request.password());
        if (!hasProfileInput(request)) {
            userService.create(loginId, password);
            return;
        }

        UserProfileValues profileValues
                = toProfileValues(request.name(), request.birthDate(), request.email());
        userService.createWithProfile(loginId, password, profileValues);
    }

    private Password encodePassword(String password) {
        RawPassword rawPassword = RawPassword.of(password);
        String encodedPassword = passwordEncoder.encode(rawPassword.value());
        return Password.of(encodedPassword);
    }

    private boolean hasProfileInput(UserCreateRequest request) {
        return request.name() != null
                || request.birthDate() != null
                || request.email() != null;
    }

    private UserProfileValues toProfileValues(String name, LocalDate birthDate, String email) {
        return new UserProfileValues(
                name != null ? UserName.of(name) : null,
                birthDate != null ? BirthDate.of(birthDate) : null,
                email != null ? Email.of(email) : null
        );
    }
}