package com.totvs.gestao_contas_service.application.dto.usuario;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioResponseTest {

    @Test
    void deveCriarResponse() {
        var id = UUID.randomUUID();
        var agora = LocalDateTime.now();
        var response = new UsuarioResponse(id, "João", "joao@email.com", agora, agora);

        assertEquals(id, response.id());
        assertEquals("João", response.nome());
        assertEquals("joao@email.com", response.email());
        assertEquals(agora, response.criadoEm());
        assertEquals(agora, response.atualizadoEm());
    }
}
