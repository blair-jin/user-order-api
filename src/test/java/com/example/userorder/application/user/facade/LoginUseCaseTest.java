package com.example.userorder.application.user.facade;

import com.example.userorder.application.auth.LoginFailService;
import com.example.userorder.application.user.service.UserQueryService;
import com.example.userorder.common.exception.InvalidLoginException;
import com.example.userorder.dto.user.UserLoginRequest;
import com.example.userorder.dto.user.UserLoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {
    @Mock
    private LoginFailService loginFailService;

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    void shouldLoginSuccessfully() {
        // given
        UserLoginRequest request = new UserLoginRequest("testId", "rawPassword");

        when(loginFailService.isBlocked(any()))
                .thenReturn(false);
        when(userQueryService.login(any()))
                .thenReturn("token");

        // when
        UserLoginResponse response = loginUseCase.execute(request);

        // then
        verify(loginFailService).resetFailCount(any());
        verify(loginFailService, never()).increaseFailCount(any());
        assertThat(response.token())
                .isEqualTo("token");
    }

    @Test
    void shouldIncreaseFailCountWhenLoginFails() {
        // given
        UserLoginRequest request = new UserLoginRequest("testId", "wrongPassword");

        when(loginFailService.isBlocked(any()))
                .thenReturn(false);
        when(userQueryService.login(any()))
                .thenThrow(InvalidLoginException.class);


        // when & then
        assertThatThrownBy(() -> loginUseCase.execute(request))
                .isInstanceOf(InvalidLoginException.class);

        verify(userQueryService).login(any());
        verify(loginFailService).increaseFailCount(any());
        verify(loginFailService, never()).resetFailCount(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountIsBlocked() {
        // given
        UserLoginRequest request = new UserLoginRequest("testId", "wrongPassword");

        when(loginFailService.isBlocked(any()))
                .thenReturn(true);

        // when & then
        assertThatThrownBy(() -> loginUseCase.execute(request))
                .isInstanceOf(InvalidLoginException.class);

        verify(loginFailService).isBlocked(any());
        verify(userQueryService, never()).login(any());
        verify(loginFailService, never()).resetFailCount(any());
        verify(loginFailService, never()).increaseFailCount(any());

    }
}