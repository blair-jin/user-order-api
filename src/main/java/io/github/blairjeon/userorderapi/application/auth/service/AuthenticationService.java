package io.github.blairjeon.userorderapi.application.auth.service;

import io.github.blairjeon.userorderapi.application.user.reader.UserReader;
import io.github.blairjeon.userorderapi.domain.user.User;
import io.github.blairjeon.userorderapi.domain.user.vo.LoginId;
import io.github.blairjeon.userorderapi.domain.user.vo.Password;
import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidLoginException;
import io.github.blairjeon.userorderapi.exception.NOT_FOUND.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;
    private final LoginFailService loginFailService;

    public User authenticate(LoginId loginId, Password password) {
        loginFailService.validateNotLocked(loginId);

        User user;

        try {
            user = userReader.getUserByLoginId(loginId);
        } catch (UserNotFoundException e) {
            loginFailService.increase(loginId);
            throw new InvalidLoginException();
        }

        if (!passwordEncoder.matches(password.value(), user.getEncodedPassword())) {
            loginFailService.increase(loginId);
            throw new InvalidLoginException();
        }

        loginFailService.reset(loginId);
        return user;
    }
}
