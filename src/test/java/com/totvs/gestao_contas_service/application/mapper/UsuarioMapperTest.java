package com.totvs.gestao_contas_service.application.mapper;

import com.totvs.gestao_contas_service.domain.entity.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private final UsuarioMapper mapper = new UsuarioMapper();

    @Test
    void deveMapearUsuarioParaResponse() {
        var id = UUID.randomUUID();
        var agora = LocalDateTime.now();
        var usuario = new Usuario(id, "João", "joao@email.com", "hash123", agora, agora);

        var response = mapper.toResponse(usuario);

        assertEquals(id, response.id());
        assertEquals("João", response.nome());
        assertEquals("joao@email.com", response.email());
        assertEquals(agora, response.criadoEm());
        assertEquals(agora, response.atualizadoEm());
    }
}
