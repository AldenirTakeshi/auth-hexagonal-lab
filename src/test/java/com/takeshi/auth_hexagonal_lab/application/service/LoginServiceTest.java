package com.takeshi.auth_hexagonal_lab.application.service;


import com.takeshi.auth_hexagonal_lab.application.ports.out.PasswordEncoderPort;
import com.takeshi.auth_hexagonal_lab.application.ports.out.TokenProviderPort;
import com.takeshi.auth_hexagonal_lab.application.ports.out.UserRepositoryPort;
import com.takeshi.auth_hexagonal_lab.domain.exception.InvalidCredentialsException;
import com.takeshi.auth_hexagonal_lab.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private TokenProviderPort tokenProviderPort;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private LoginService loginService;

    @Test
    void deveRetornarTokenQuandoCredenciaisValidas(){
        User user = new User("1", "Takeshi", "takeshi@gmail.com", "hash-da-senha");
        when(userRepositoryPort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches("12345", user.getPassword())).thenReturn(true);
        when(tokenProviderPort.generateToken(user)).thenReturn("token-falso");

        String token = loginService.execute(user.getEmail(), "12345");

        assertEquals("token-falso", token);
    }

    @Test
    void emailDeveSerInexistente(){
        User user = new User("1", "Takeshi", "aldenir@gmail.com", "123456");
        when(userRepositoryPort.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThrows(InvalidCredentialsException.class, () -> loginService.execute(user.getEmail(), "123456"));
    }

    @Test
    void deveLancarExcecaoQuandoSenhaErrada(){
        User user = new User("1", "Takeshi", "takeshi@gmail.com", "hash-da-senha");

        when(userRepositoryPort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches("senha-errada", user.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> loginService.execute(user.getEmail(), "senha-errada"));
    }

}
