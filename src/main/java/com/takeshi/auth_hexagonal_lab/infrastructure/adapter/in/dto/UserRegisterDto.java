package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.dto;

public record UserRegisterDto(
        String name,
        String email,
        String password
) {
}
