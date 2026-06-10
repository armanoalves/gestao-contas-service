package com.totvs.gestao_contas_service.domain.event;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ImportacaoSolicitadaEventTest {

    @Test
    void deveCriarEvent() {
        var protocolo = UUID.randomUUID();
        var conteudo = "csv content".getBytes();
        var event = new ImportacaoSolicitadaEvent(protocolo, conteudo);

        assertEquals(protocolo, event.protocolo());
        assertArrayEquals(conteudo, event.conteudoCsv());
    }

    @Test
    void deveAceitarConteudoVazio() {
        var protocolo = UUID.randomUUID();
        var event = new ImportacaoSolicitadaEvent(protocolo, new byte[0]);

        assertEquals(0, event.conteudoCsv().length);
    }
}
