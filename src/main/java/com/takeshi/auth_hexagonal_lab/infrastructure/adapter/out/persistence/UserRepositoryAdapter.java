package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.out.persistence;

import com.takeshi.auth_hexagonal_lab.application.ports.out.UserRepositoryPort;
import com.takeshi.auth_hexagonal_lab.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final Map<String, User> database = new HashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        User userWithEmail = database.get(email);
        return Optional.ofNullable(userWithEmail);
    }

    @Override
    public void save(User user) {
        database.put(user.getEmail(), user);
    }
}
