package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.out.jwt;

import com.takeshi.auth_hexagonal_lab.application.ports.out.TokenProviderPort;
import com.takeshi.auth_hexagonal_lab.domain.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenAdapter implements TokenProviderPort {

    private final SecretKey secret;
    private final long expirationMs;

    public JwtTokenAdapter(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMs = expirationMs;
    }

    @Override
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secret)
                .compact();
    }
}
