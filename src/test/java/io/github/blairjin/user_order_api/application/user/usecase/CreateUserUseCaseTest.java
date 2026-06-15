package io.github.blairjin.user_order_api.application.user.usecase;

import io.github.blairjin.user_order_api.application.user.command.UserCreateCommand;
import io.github.blairjin.user_order_api.application.user.command.UserProfileCreateCommand;
import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.application.user.service.UserCommandService;
import io.github.blairjin.user_order_api.domain.user.vo.LoginId;
import io.github.blairjin.user_order_api.dto.user.UserCreateRequest;
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