package com.example.userorder.domain.user;

import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.Password;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserTest {
    @Test
    void shouldCreateUser() {
        LoginId loginId = LoginId.of("test1234");
        Password password = Password.of("encoded-password-123-@");

        User user = User.create(loginId, password);

        assertThat(user.getLoginId())
                .isEqualTo("test1234");
        assertThat(user.getPassword())
                .isEqualTo("encoded-password-123-@");
        assertThat(user.getRole())
                .isEqualTo(Role.USER);
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsNull() {
        LoginId loginId = null;
        Password password = Password.of("encoded-password-123-@");

        assertThatThrownBy(() -> User.create(loginId, password))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        LoginId loginId = LoginId.of("test1234");
        Password password = null;

        assertThatThrownBy(() -> User.create(loginId, password))
                .isInstanceOf(NullPointerException.class);

    }
}