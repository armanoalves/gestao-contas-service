package com.totvs.gestao_contas_service.application.dto.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void deveCriarRequest() {
        var request = new LoginRequest("joao@email.com", "senha123");

        assertEquals("joao@email.com", request.email());
        assertEquals("senha123", request.senha());
    }
}
