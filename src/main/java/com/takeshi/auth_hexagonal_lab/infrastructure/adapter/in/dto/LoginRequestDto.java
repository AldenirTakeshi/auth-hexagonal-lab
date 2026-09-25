package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.dto;

public record LoginRequestDto(
        String email,
        String password
) {
}
