package com.example.userorder.domain.user;

import com.example.userorder.application.user.model.UserProfileValues;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserProfileTest {
    @Test
    void shouldCreateProfile() {
        Long userId = 1L;
        String name = "testName";
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        String email = "test@test.com";

        UserProfileValues profileValues = UserProfileValues.of(name, birthDate, email);

        UserProfile profile = UserProfile.create(userId, profileValues);

        assertThat(profile.getUserId())
                .isEqualTo(1L);
        assertThat(profile.getName())
                .isEqualTo("testName");
        assertThat(profile.getBirthDate())
                .isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(profile.getEmail())
                .isEqualTo("test@test.com");
    }

    @Test
    void shouldThrowExceptionWhenProfileValuesIsNull() {
        Long userId = 1L;
        UserProfileValues profileValues = null;

        assertThatThrownBy(() -> UserProfile.create(userId, profileValues))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowExceptionWhenProfileValuesIsEmpty() {
        Long userId = 1L;
        UserProfileValues profileValues = UserProfileValues.of(null, null, null);

        assertThatThrownBy(() -> UserProfile.create(userId, profileValues))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        Long userId = null;
        UserProfileValues profileValues = UserProfileValues.of(
                "test",
                LocalDate.of(2000, 1, 1),
                "test@test.com"
        );

        assertThatThrownBy(() -> UserProfile.create(userId, profileValues))
                .isInstanceOf(NullPointerException.class);
    }
}
