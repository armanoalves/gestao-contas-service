package com.totvs.gestao_contas_service.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(
                "01234567890123456789012345678901234567890123456789012345678901234567890123456789",
                86400000
        );
    }

    @Test
    void deveGerarEValidarToken() {
        var usuarioId = UUID.randomUUID();
        var email = "teste@email.com";

        var token = jwtUtil.gerarToken(usuarioId, email);

        assertNotNull(token);
        assertTrue(jwtUtil.tokenValido(token));
        assertEquals(usuarioId, jwtUtil.extrairUsuarioId(token));
    }

    @Test
    void deveRejeitarTokenInvalido() {
        assertFalse(jwtUtil.tokenValido("token.invalido.aqui"));
    }

    @Test
    void deveRejeitarTokenVazio() {
        assertFalse(jwtUtil.tokenValido(""));
    }

    @Test
    void deveRejeitarTokenNulo() {
        assertFalse(jwtUtil.tokenValido(null));
    }
}
