package com.takeshi.auth_hexagonal_lab.application.service;

import com.takeshi.auth_hexagonal_lab.application.ports.in.LoginUseCase;
import com.takeshi.auth_hexagonal_lab.application.ports.out.PasswordEncoderPort;
import com.takeshi.auth_hexagonal_lab.application.ports.out.TokenProviderPort;
import com.takeshi.auth_hexagonal_lab.application.ports.out.UserRepositoryPort;
import com.takeshi.auth_hexagonal_lab.domain.exception.InvalidCredentialsException;
import com.takeshi.auth_hexagonal_lab.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProviderPort tokenProviderPort;

    public LoginService(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort, TokenProviderPort tokenProviderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public String execute(String email, String password) {
        User user = userRepositoryPort.findByEmail(email).orElseThrow(() -> {
            throw new InvalidCredentialsException("Credentials invalid!");
        });
        if (passwordEncoderPort.matches(password, user.getPassword())){
            return tokenProviderPort.generateToken(user);
        }else {
             throw new InvalidCredentialsException("Credentials invalid!");
        }
    }
}
