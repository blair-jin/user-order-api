package io.github.blairjeon.userorderapi.domain.user.vo;

import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidValueException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BirthDateTest {
    @Test
    void shouldCreateBirthDateSuccessfully(){
        LocalDate validBirthDate = LocalDate.now().minusYears(20);

        BirthDate birthDate = BirthDate.of(validBirthDate);

        assertThat(birthDate.value()).isEqualTo(validBirthDate);
    }

    @Test
    void shouldThrowExceptionWhenBirthDateIsNull(){
        assertThatThrownBy(() -> BirthDate.of(null))
                .isInstanceOf(InvalidValueException.class);
    }

    @Test
    void shouldThrowExceptionWhenBirthDateIsFuture(){
        LocalDate futureBirthDate = LocalDate.now().plusDays(1);

        assertThatThrownBy(() -> BirthDate.of(futureBirthDate))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("future");
    }

    @Test
    void shouldThrowExceptionWhenBirthDateIsUnder14YearsOld(){
        LocalDate underAgeBirthDate = LocalDate.now().minusYears(13);

        assertThatThrownBy(() -> BirthDate.of(underAgeBirthDate))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("14");
    }
}