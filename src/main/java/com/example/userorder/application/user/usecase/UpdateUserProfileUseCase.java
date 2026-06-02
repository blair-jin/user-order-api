package com.example.userorder.application.user.usecase;

import com.example.userorder.application.user.model.UserProfileValues;
import com.example.userorder.application.user.service.UserCommandService;
import com.example.userorder.domain.user.vo.BirthDate;
import com.example.userorder.domain.user.vo.Email;
import com.example.userorder.domain.user.vo.UserName;
import com.example.userorder.dto.user.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {
    private final UserCommandService userCommandService;

    public void execute(Long userId, UserProfileUpdateRequest request) {
        if (!hasProfileInput(request)) {
            throw new IllegalArgumentException("PROFILE_UPDATE_EMPTY");
        }

        UserProfileValues profileValues
                = toProfileValues(request.name(), request.birthDate(), request.email());

        userCommandService.updateProfile(userId, profileValues);
    }

    private boolean hasProfileInput(UserProfileUpdateRequest request) {
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