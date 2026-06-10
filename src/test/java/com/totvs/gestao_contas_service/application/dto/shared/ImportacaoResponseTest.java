package com.totvs.gestao_contas_service.application.dto.shared;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ImportacaoResponseTest {

    @Test
    void deveCriarResponse() {
        var protocoloId = UUID.randomUUID();
        var response = new ImportacaoResponse(protocoloId, "Arquivo recebido");

        assertEquals(protocoloId, response.protocoloId());
        assertEquals("Arquivo recebido", response.mensagem());
    }
}
