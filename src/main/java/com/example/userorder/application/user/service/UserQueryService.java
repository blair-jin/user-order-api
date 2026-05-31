package com.example.userorder.application.user.service;

import com.example.userorder.application.user.command.LoginCommand;
import com.example.userorder.application.user.reader.UserReader;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.domain.user.User;
import com.example.userorder.domain.user.UserProfile;
import com.example.userorder.dto.user.UserProfileResponse;
import com.example.userorder.dto.user.UserResponse;
import com.example.userorder.security.JwtUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;

    public JwtUserInfo login(LoginCommand command) {
        User user = userReader.getByLoginIdForLogin(command.loginId());

        if (!passwordEncoder.matches(command.rawPassword().value(), user.getPassword())) {
            throw new InvalidLoginException();
        }

        JwtUserInfo userInfo = new JwtUserInfo(user.getId(), user.getLoginId(), user.getRole());
        return userInfo;
    }

    public UserResponse get(Long userId) {
        User user = userReader.getById(userId);
        return UserResponse.from(user);
    }

    public UserProfileResponse getProfile(Long userId) {
        UserProfile profile = userReader.getProfileByUserId(userId);
        return UserProfileResponse.from(profile);
    }

    public JwtUserInfo getJwtUserInfo(Long userId) {
        User user = userReader.getById(userId);
        return new JwtUserInfo(user.getId(), user.getLoginId(), user.getRole());
    }
}