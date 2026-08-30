package io.github.blairjeon.userorderapi.application.user.usecase;

import io.github.blairjeon.userorderapi.application.user.reader.UserReader;
import io.github.blairjeon.userorderapi.domain.user.UserProfile;
import io.github.blairjeon.userorderapi.dto.user.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserProfileUseCase {
    private final UserReader userReader;

    public UserProfileResponse execute(Long userId){
        UserProfile profile = userReader.getUserProfileByUserId(userId);
        return UserProfileResponse.from(profile);
    }
}