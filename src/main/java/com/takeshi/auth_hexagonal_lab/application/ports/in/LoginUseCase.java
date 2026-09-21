package com.takeshi.auth_hexagonal_lab.application.ports.in;

public interface LoginUseCase {
     String execute(String email, String password);
}
