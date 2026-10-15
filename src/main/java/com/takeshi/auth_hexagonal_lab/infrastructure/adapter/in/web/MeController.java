package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {
    @GetMapping("/me")
    public String me(HttpServletRequest request){
        return "Logado como: " + request.getAttribute("userEmail");
    }
}
