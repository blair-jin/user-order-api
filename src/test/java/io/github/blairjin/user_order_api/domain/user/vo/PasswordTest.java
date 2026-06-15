package io.github.blairjin.user_order_api.domain.user.vo;

import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PasswordTest {
    @Test
    void shouldCreatePasswordSuccessfully(){
        String validPassword = "testPassword";

        Password password = Password.of(validPassword);

        assertThat(password.value()).isEqualTo(validPassword);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull(){
        assertThatThrownBy(() -> Password.of(null))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("null");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsBlank(){
        String blankPassword = " ";

        assertThatThrownBy(() -> Password.of(blankPassword))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("blank");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsTooShort(){
        String shortPassword = "A".repeat(4);

        assertThatThrownBy(() -> Password.of(shortPassword))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("5");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsTooLong(){
        String longPassword = "A".repeat(33);

        assertThatThrownBy(() -> Password.of(longPassword))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("32");
    }
}