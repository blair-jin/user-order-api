package com.example.userorder.domain.user.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LoginIdTest {
    @Test
    void shouldCreateLoginId() {
        LoginId loginId = LoginId.of("test1234");

        assertThat(loginId.value())
                .isEqualTo("test1234");
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsNull() {
        assertThatThrownBy(() -> LoginId.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsTooShort() {
        assertThatThrownBy(() -> LoginId.of("test"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsTooLong() {
        assertThatThrownBy(() -> LoginId.of("abc_def_ghi_jkl_mno_pqr_stu_vwx_yzz"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}