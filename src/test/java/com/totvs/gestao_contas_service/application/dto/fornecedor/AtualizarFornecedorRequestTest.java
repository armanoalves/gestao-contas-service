package com.totvs.gestao_contas_service.application.dto.fornecedor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtualizarFornecedorRequestTest {

    @Test
    void deveCriarRequest() {
        var request = new AtualizarFornecedorRequest("Fornecedor Atualizado");
        assertEquals("Fornecedor Atualizado", request.nome());
    }
}
