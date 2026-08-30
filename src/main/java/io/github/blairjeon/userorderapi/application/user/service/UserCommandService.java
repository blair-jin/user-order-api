package io.github.blairjeon.userorderapi.application.user.service;

import io.github.blairjeon.userorderapi.application.user.command.UserCreateCommand;
import io.github.blairjeon.userorderapi.application.user.command.UserProfileCreateCommand;
import io.github.blairjeon.userorderapi.application.user.command.UserProfileUpdateCommand;
import io.github.blairjeon.userorderapi.application.user.reader.UserReader;
import io.github.blairjeon.userorderapi.domain.user.User;
import io.github.blairjeon.userorderapi.domain.user.UserProfile;
import io.github.blairjeon.userorderapi.domain.user.vo.Email;
import io.github.blairjeon.userorderapi.repository.user.UserProfileRepository;
import io.github.blairjeon.userorderapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserReader userReader;

    @Transactional
    public void create(UserCreateCommand userCreateCommand, UserProfileCreateCommand profileCommand){
        User user = User.create(userCreateCommand);
        User savedUser = userRepository.save(user);

        UserProfile profile = UserProfile.create(savedUser.getId(), profileCommand);
        userProfileRepository.save(profile);
    }

    @Transactional
    public void update(Long userId, Email email){
        User user = userReader.getUserById(userId);
        user.updateEmail(email);
    }

    @Transactional
    public void updateProfile(Long userId, UserProfileUpdateCommand command){
        UserProfile profile = userReader.getUserProfileByUserId(userId);
        profile.update(command);
    }

    @Transactional
    public void delete(Long userId){
        User user = userReader.getUserById(userId);
        UserProfile profile = userReader.getUserProfileByUserId(userId);
        // TODO: Address entity

        userRepository.delete(user);
        userProfileRepository.delete(profile);
    }
}