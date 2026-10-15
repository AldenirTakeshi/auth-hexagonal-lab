package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.in.web.security;

import com.takeshi.auth_hexagonal_lab.application.ports.out.TokenProviderPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> ROTAS_PUBLICAS = List.of("/login", "/register");
    private final TokenProviderPort tokenProviderPort;

    public JwtAuthenticationFilter(TokenProviderPort tokenProviderPort) {
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (ROTAS_PUBLICAS.contains(request.getRequestURI())){
                filterChain.doFilter(request, response);
                return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = tokenProviderPort.validateTokenAndGetSubject(token);
            request.setAttribute("userEmail", email);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
