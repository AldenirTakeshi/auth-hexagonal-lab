package com.takeshi.auth_hexagonal_lab.application.ports.out;

import com.takeshi.auth_hexagonal_lab.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    void save(User user);
}
