package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.web;


import com.takeshi.auth_hexagonal_lab.application.ports.in.RegisterUserUseCase;
import com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.dto.RegisterRequestDto;
import com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.dto.RegisterResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final RegisterUserUseCase registerUserUseCase;

    public RegisterController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping
    public RegisterResponseDto register(@RequestBody RegisterRequestDto userRegisterDto){
        var user = registerUserUseCase.execute(userRegisterDto.name(), userRegisterDto.email(), userRegisterDto.password());

        RegisterResponseDto userResponseDto = new RegisterResponseDto(user.getId(), user.getName(), user.getEmail());

        return userResponseDto;
    }
}
