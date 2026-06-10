package com.totvs.gestao_contas_service.application.dto.fornecedor;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FornecedorResponseTest {

    @Test
    void deveCriarResponse() {
        var id = UUID.randomUUID();
        var agora = LocalDateTime.now();
        var response = new FornecedorResponse(id, "Fornecedor", agora, agora);

        assertEquals(id, response.id());
        assertEquals("Fornecedor", response.nome());
        assertEquals(agora, response.criadoEm());
        assertEquals(agora, response.atualizadoEm());
    }
}
