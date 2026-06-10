package com.totvs.gestao_contas_service.application.dto.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    void deveCriarResponseComToken() {
        var response = new LoginResponse("eyJhbGciOiJIUzI1NiJ9.token");

        assertEquals("eyJhbGciOiJIUzI1NiJ9.token", response.token());
        assertEquals("Bearer", response.tipo());
    }

    @Test
    void deveCriarResponseComTipoPersonalizado() {
        var response = new LoginResponse("token", "Custom");

        assertEquals("token", response.token());
        assertEquals("Custom", response.tipo());
    }
}
