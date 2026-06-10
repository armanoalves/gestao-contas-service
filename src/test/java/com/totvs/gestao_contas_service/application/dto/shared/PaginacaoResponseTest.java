package com.totvs.gestao_contas_service.application.dto.shared;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginacaoResponseTest {

    @Test
    void deveCriarResponseComListaVazia() {
        var response = new PaginacaoResponse<>(List.of(), 0, 20, 0, 0);

        assertTrue(response.conteudo().isEmpty());
        assertEquals(0, response.pagina());
        assertEquals(20, response.tamanho());
        assertEquals(0, response.totalElementos());
        assertEquals(0, response.totalPaginas());
    }

    @Test
    void deveCriarResponseComDados() {
        var response = new PaginacaoResponse<>(List.of("a", "b"), 1, 10, 2, 1);

        assertEquals(2, response.conteudo().size());
        assertEquals(1, response.pagina());
        assertEquals(10, response.tamanho());
        assertEquals(2, response.totalElementos());
        assertEquals(1, response.totalPaginas());
    }
}
