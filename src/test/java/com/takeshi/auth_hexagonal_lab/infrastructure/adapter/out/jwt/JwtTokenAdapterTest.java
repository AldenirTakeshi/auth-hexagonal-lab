package com.takeshi.auth_hexagonal_lab.infrastructure.adapter.out.jwt;

import com.takeshi.auth_hexagonal_lab.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenAdapterTest {
    private static final String TEST_SECRET = "mV1pZ8bLz44LS4f0ZHXq84cI0Lz0XaGG74SHvQGbGjw=";
    private static final long TEST_EXPIRATION_MS = 3600000;

    private JwtTokenAdapter jwtTokenAdapter;

    @BeforeEach
    void setUp(){
        jwtTokenAdapter = new JwtTokenAdapter(TEST_SECRET, TEST_EXPIRATION_MS);
    }

    @Test
    void deveGerarTokenVazio(){
        User user = new User("1", "Takeshi", "takeshi@gmail.com", "senha-hash");

        String token = jwtTokenAdapter.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void tokenDeveTerFormatoJwtValido(){
        User user = new User("1", "Takeshi", "takeshi@gmail.com", "senha-hash");

        String token = jwtTokenAdapter.generateToken(user);

        String[] partes = token.split("\\.");
        assertEquals(3, partes.length);
    }

    @Test
    void tokenDeveConterEmailComoSubject(){
        User user = new User("42", "maria", "maria@gmail.com", "senha-hash");

        String token = jwtTokenAdapter.generateToken(user);

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(TEST_SECRET));

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertEquals("maria@gmail.com", claims.getSubject());
        assertEquals("42", claims.get("id"));
        assertEquals("maria", claims.get("name"));
    }

    @Test
    void tokenDeveExpirarNoFuturo(){
        User user = new User("1", "Takeshi", "takeshi@gmail.com", "senha-hash");

        String token = jwtTokenAdapter.generateToken(user);

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(TEST_SECRET));

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertTrue(claims.getExpiration().after(new Date()));
    }

}
