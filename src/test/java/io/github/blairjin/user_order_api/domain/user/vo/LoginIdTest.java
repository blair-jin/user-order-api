package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LoginIdTest {
    @Test
    void shouldCreateLoginIdSuccessfully(){
        String value = "test1234";

        LoginId loginId = LoginId.of(value);

        assertThat(loginId.value()).isEqualTo(value);
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsNull(){
        assertThatThrownBy(() -> LoginId.of(null))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("null");
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsBlank(){
        String blankLoginId = " ";

        assertThatThrownBy(() -> LoginId.of(blankLoginId))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("blank");
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsTooShort(){
        String tooShortLoginId = "AB";

        assertThatThrownBy(() -> LoginId.of(tooShortLoginId))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("5");
    }

    @Test
    void shouldThrowExceptionWhenLoginIdIsTooLong(){
        String tooLongLoginId = "A".repeat(33);

        assertThatThrownBy(() -> LoginId.of(tooLongLoginId))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("32");
    }
}