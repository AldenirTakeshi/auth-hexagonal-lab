package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.web;

import com.takeshi.auth_hexagonal_lab.application.ports.in.LoginUseCase;
import com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.dto.LoginRequestDto;
import com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.dto.LoginResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        String token = loginUseCase.execute(loginRequestDto.email(), loginRequestDto.password());
        return new LoginResponseDto(token);
    }
}
