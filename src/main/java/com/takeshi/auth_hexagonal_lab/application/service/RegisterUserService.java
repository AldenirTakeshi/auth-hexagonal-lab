package com.takeshi.auth_hexagonal_lab.application.service;

import com.takeshi.auth_hexagonal_lab.application.ports.in.RegisterUserUseCase;
import com.takeshi.auth_hexagonal_lab.application.ports.out.PasswordEncoderPort;
import com.takeshi.auth_hexagonal_lab.application.ports.out.UserRepositoryPort;
import com.takeshi.auth_hexagonal_lab.domain.exception.EmailAlreadyRegisterException;
import com.takeshi.auth_hexagonal_lab.domain.model.User;

import java.util.UUID;

public class RegisterUserService implements RegisterUserUseCase{

    private final PasswordEncoderPort passwordEncoderPort;
    private final UserRepositoryPort userRepositoryPort;

    public RegisterUserService(PasswordEncoderPort passwordEncoderPort, UserRepositoryPort userRepositoryPort) {
        this.passwordEncoderPort = passwordEncoderPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User execute(String name, String email, String password) {
        userRepositoryPort.findByEmail(email).ifPresent(user -> {
            throw new EmailAlreadyRegisterException("Email ja existente " + email);
        });

        String hashedPassword = passwordEncoderPort.encode(password);
        String randomId = UUID.randomUUID().toString();

        User user = new User(randomId, name, email, hashedPassword);

        userRepositoryPort.save(user);

        return user;
    }
}
