package com.totvs.gestao_contas_service.application.dto.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtualizarUsuarioRequestTest {

    @Test
    void deveCriarRequest() {
        var request = new AtualizarUsuarioRequest("João", "joao@email.com");

        assertEquals("João", request.nome());
        assertEquals("joao@email.com", request.email());
    }
}
