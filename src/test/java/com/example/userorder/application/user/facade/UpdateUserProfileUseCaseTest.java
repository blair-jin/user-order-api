package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.model.UserProfileValues;
import com.example.userorder.application.user.service.UserCommandService;
import com.example.userorder.dto.user.UserProfileUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateUserProfileUseCaseTest {
    @Mock
    private UserCommandService userCommandService;

    @InjectMocks
    private UpdateUserProfileUseCase updateUserProfileUseCase;

    @Test
    void shouldUpdateProfile() {
        // given
        Long userId = 1L;
        UserProfileUpdateRequest request =
                new UserProfileUpdateRequest(
                        "Test Name",
                        LocalDate.of(2000, 1, 1),
                        "test@test.com"
                );

        // when
        updateUserProfileUseCase.execute(userId, request);

        // then
        verify(userCommandService)
                .updateProfile(eq(userId), any(UserProfileValues.class));
    }

    @Test
    void shouldThrowExceptionWhenProfileUpdateIsEmpty() {
        // given
        Long userId = 1L;
        UserProfileUpdateRequest request =
                new UserProfileUpdateRequest(null, null, null);

        // when & then
        assertThatThrownBy(() -> updateUserProfileUseCase.execute(userId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROFILE_UPDATE_EMPTY");

        verify(userCommandService, never())
                .updateProfile(any(), any());
    }
}