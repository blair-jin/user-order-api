package com.example.userorder.application.user.facade;

import com.example.userorder.application.user.command.CreateUserCommand;
import com.example.userorder.application.user.reader.UserReader;
import com.example.userorder.application.user.service.UserCommandService;
import com.example.userorder.common.exception.DuplicateLoginIdException;
import com.example.userorder.dto.user.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {
    @Mock
    private UserReader userReader;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserCommandService userCommandService;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Captor
    private ArgumentCaptor<CreateUserCommand> commandCaptor;

    @Test
    void shouldCreateUser() {
        // given
        UserCreateRequest request = new UserCreateRequest(
                "test1234",
                "password123!",
                "Test Name",
                LocalDate.of(2000, 1, 1),
                "test@test.com"
        );

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encoded-password");

        // when
        createUserUseCase.execute(request);

        // then
        verify(userCommandService).create(commandCaptor.capture());
        CreateUserCommand command = commandCaptor.getValue();
        assertThat(command.loginId().value())
                .isEqualTo("test1234");
        assertThat(command.encodedPassword().value())
                .isEqualTo("encoded-password");
        assertThat(command.profileValues().isEmpty())
                .isFalse();
    }

    @Test
    void shouldThrowExceptionWhenLoginIdAlreadyExists() {
        // given
        UserCreateRequest request = new UserCreateRequest(
                "test1234",
                "password123!",
                "Test Name",
                LocalDate.of(2000, 1, 1),
                "test@test.com"
        );

        doThrow(new DuplicateLoginIdException())
                .when(userReader)
                .validateLoginIdAvailable(any());

        // when
        assertThatThrownBy(() -> createUserUseCase.execute(request))
                .isInstanceOf(DuplicateLoginIdException.class);

        // then
        verify(userCommandService, never()).create(any());
        verify(passwordEncoder, never()).encode(anyString());
    }
}