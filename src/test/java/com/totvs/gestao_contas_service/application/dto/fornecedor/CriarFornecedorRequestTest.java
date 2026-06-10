package com.totvs.gestao_contas_service.application.dto.fornecedor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriarFornecedorRequestTest {

    @Test
    void deveCriarRequest() {
        var request = new CriarFornecedorRequest("Fornecedor Teste");
        assertEquals("Fornecedor Teste", request.nome());
    }

    @Test
    void deveAceitarNomeComAcentos() {
        var request = new CriarFornecedorRequest("Fornecedor Ltda.");
        assertEquals("Fornecedor Ltda.", request.nome());
    }
}
