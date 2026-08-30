package io.github.blairjeon.userorderapi.domain.common.vo;

import io.github.blairjeon.userorderapi.exception.BAD_REQUEST.InvalidValueException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MoneyTest {
    @Test
    void shouldCreateMoneySuccess(){
        long validMoney = 1234;

        Money money = Money.of(validMoney);

        assertThat(money.value()).isEqualTo(validMoney);
    }

    @Test
    void shouldThrowExceptionWhenMoneyIsNegative(){
        long negativeMoney = -1;

        assertThatThrownBy(() -> Money.of(negativeMoney))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("negative");
    }
}
