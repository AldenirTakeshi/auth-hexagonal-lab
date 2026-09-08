package com.takeshi.auth_hexagonal_lab.application.ports.in;

import com.takeshi.auth_hexagonal_lab.domain.model.User;

public interface RegisterUserUseCase {
    User execute(String name, String email, String password);
}
