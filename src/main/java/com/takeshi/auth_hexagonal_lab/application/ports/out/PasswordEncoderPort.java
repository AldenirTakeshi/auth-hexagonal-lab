package com.takeshi.auth_hexagonal_lab.application.ports.out;

public interface PasswordEncoderPort {
    String encode(String password);
    boolean matches(String password, String userPassword);
}
