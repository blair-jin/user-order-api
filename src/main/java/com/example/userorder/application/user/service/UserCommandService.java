package com.example.userorder.application.user.service;

import com.example.userorder.application.user.command.CreateUserCommand;
import com.example.userorder.application.user.model.UserProfileValues;
import com.example.userorder.application.user.reader.UserReader;
import com.example.userorder.common.exception.DuplicateLoginIdException;
import com.example.userorder.domain.user.User;
import com.example.userorder.domain.user.UserProfile;
import com.example.userorder.repository.user.UserProfileRepository;
import com.example.userorder.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserReader userReader;

    public void create(CreateUserCommand command) {
        User user = User.create(command.loginId(), command.encodedPassword());

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateLoginIdException();
        }

        if (command.profileValues().isEmpty()) {
            return;
        }

        UserProfile profile = UserProfile.create(user.getId(), command.profileValues());
        userProfileRepository.save(profile);
    }

    public void updateProfile(Long userId, UserProfileValues request) {
        UserProfile profile = userReader.getProfileByUserId(userId);

        profile.updateProfile(request.userName(), request.birthDate(), request.email());
    }

    public void delete(Long userId) {
        User user = userReader.getById(userId);
        userRepository.delete(user);

        if (userReader.hasProfileById(user.getId())) {
            UserProfile profile = userReader.getProfileByUserId(user.getId());
            userProfileRepository.delete(profile);
        }
    }

    public void deleteProfile(Long userId) {
        UserProfile profile = userReader.getProfileByUserId(userId);
        userProfileRepository.delete(profile);
    }
}