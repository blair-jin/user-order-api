package com.example.userorder.application.user.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserProfileValuesTest {
    @Test
    void shouldCreateUserProfileValues() {
        UserProfileValues profileValues = UserProfileValues.of(
                "Test Name",
                LocalDate.of(2000, 1, 1),
                "test@test.com"
        );

        assertThat(profileValues.userName().value())
                .isEqualTo("Test Name");
        assertThat(profileValues.birthDate().value())
                .isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(profileValues.email().value())
                .isEqualTo("test@test.com");
    }

    @Test
    void shouldReturnTrueWhenAllValuesAreNull() {
        UserProfileValues profileValues = UserProfileValues.of(null, null, null);
        assertThat(profileValues.isEmpty()).isTrue();
    }

}
