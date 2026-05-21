package com.example.userorder.application.user.service;

import com.example.userorder.application.user.model.UserProfileValues;
import com.example.userorder.application.user.reader.UserReader;
import com.example.userorder.common.exception.DuplicateLoginIdException;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.domain.user.User;
import com.example.userorder.domain.user.UserProfile;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.Password;
import com.example.userorder.domain.user.vo.RawPassword;
import com.example.userorder.dto.user.UserLoginResponse;
import com.example.userorder.dto.user.UserProfileResponse;
import com.example.userorder.dto.user.UserResponse;
import com.example.userorder.repository.user.UserProfileRepository;
import com.example.userorder.repository.user.UserRepository;
import com.example.userorder.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void create(LoginId loginId, Password password) {
        User user = User.create(loginId, password);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLoginIdException();
        }
    }

    @Transactional
    public void createWithProfile(LoginId loginId, Password password, UserProfileValues profileValues) {
        User user = User.create(loginId, password);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLoginIdException();
        }

        UserProfile profile = UserProfile.create(
                user.getId(),
                profileValues.userName(),
                profileValues.birthDate(),
                profileValues.email()
        );

        userProfileRepository.save(profile);

    }

    public UserLoginResponse login(LoginId loginId, RawPassword password) {
        User user = userReader.getByLoginIdForLogin(loginId);
        if (!passwordEncoder.matches(password.value(), user.getPassword())) {
            throw new InvalidLoginException();
        }
        String token = jwtProvider.createToken(user.getId(), user.getLoginId(), user.getRole());
        return new UserLoginResponse(token);
    }

    public UserResponse getUser(Long userId) {
        User user = userReader.getById(userId);
        return UserResponse.from(user);
    }

    public UserProfileResponse getProfile(Long userId) {
        UserProfile profile = userReader.getProfileByUserId(userId);
        return UserProfileResponse.from(profile);
    }

    @Transactional
    public void updateProfile(Long userId, UserProfileValues request) {
        UserProfile profile = userReader.getProfileByUserId(userId);

        profile.updateProfile(request.userName(), request.birthDate(), request.email());
    }

    @Transactional
    public void delete(Long userId) {
        User user = userReader.getById(userId);
        userRepository.delete(user);
    }

    @Transactional
    public void deleteProfile(Long userId) {
        UserProfile profile = userReader.getProfileByUserId(userId);
        userProfileRepository.delete(profile);
    }

}