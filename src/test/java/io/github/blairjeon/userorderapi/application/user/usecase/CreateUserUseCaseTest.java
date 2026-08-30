package io.github.blairjeon.userorderapi.application.user.usecase;

import io.github.blairjeon.userorderapi.application.user.command.UserCreateCommand;
import io.github.blairjeon.userorderapi.application.user.command.UserProfileCreateCommand;
import io.github.blairjeon.userorderapi.application.user.reader.UserReader;
import io.github.blairjeon.userorderapi.application.user.service.UserCommandService;
import io.github.blairjeon.userorderapi.domain.user.vo.LoginId;
import io.github.blairjeon.userorderapi.dto.user.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {
    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UserReader userReader;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserCommandService userCommandService;

    @Test
    void shouldCreateUserSuccessfully(){
        UserCreateRequest request = mock(UserCreateRequest.class);
        when(request.loginId()).thenReturn("testLoginId");
        when(request.password()).thenReturn("testPassword");
        when(request.email()).thenReturn("test@example.com");
        when(passwordEncoder.encode("testPassword"))
                .thenReturn("encodedPassword");

        createUserUseCase.execute(request);

        verify(userReader).validateDuplicateLoginId(LoginId.of("testLoginId"));
        verify(passwordEncoder).encode("testPassword");
        verify(userCommandService).create(any(UserCreateCommand.class), any(UserProfileCreateCommand.class));
    }
}