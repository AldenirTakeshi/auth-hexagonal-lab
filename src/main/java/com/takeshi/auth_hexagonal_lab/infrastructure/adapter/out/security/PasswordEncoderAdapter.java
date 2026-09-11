package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.out.security;

import com.takeshi.auth_hexagonal_lab.application.ports.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderAdapter implements PasswordEncoderPort {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String password) {
        String passwordEncoded = bCryptPasswordEncoder.encode(password);
        return passwordEncoded;
    }

    @Override
    public boolean matches(String password, String userPassword) {
        boolean matched = bCryptPasswordEncoder.matches(password, userPassword);
        return matched;
    }
}
