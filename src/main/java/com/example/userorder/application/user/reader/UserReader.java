package com.example.userorder.application.user.reader;

import com.example.userorder.common.exception.DuplicateLoginIdException;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.common.exception.UserNotFoundException;
import com.example.userorder.common.exception.UserProfileNotFoundException;
import com.example.userorder.domain.user.User;
import com.example.userorder.domain.user.UserProfile;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.repository.user.UserProfileRepository;
import com.example.userorder.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public void validateLoginIdAvailable(LoginId loginId) {
        if (userRepository.existsByLoginId(loginId.value())) {
            throw new DuplicateLoginIdException();
        }
    }

    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getByLoginIdForLogin(LoginId loginId) {
        return userRepository.findByLoginId(loginId.value())
                .orElseThrow(InvalidLoginException::new);
    }

    public boolean hasProfileById(Long userProfileId) {
        return userProfileRepository.existsById(userProfileId);
    }

    public UserProfile getProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(UserProfileNotFoundException::new);
    }
}