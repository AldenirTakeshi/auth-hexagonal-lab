package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.dto;

public record RegisterResponseDto(
        String id,
        String name,
        String email
) { }
