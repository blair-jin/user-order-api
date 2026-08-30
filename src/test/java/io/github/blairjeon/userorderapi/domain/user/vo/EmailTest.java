package io.github.blairjeon.userorderapi.domain.user.vo;

import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EmailTest {
    @Test
    void shouldCreateEmailSuccessfully(){
        String validEmail = "a@a";

        Email email = Email.of(validEmail);

        assertThat(email.value()).isEqualTo(validEmail);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull(){
        assertThatThrownBy(() -> Email.of(null))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("null");
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBlank(){
        String blankEmail = " ";

        assertThatThrownBy(() -> Email.of(blankEmail))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("blank");
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotContainAtSymbol(){
        String invalidEmail = "testemail.com";

        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("format");
    }
}