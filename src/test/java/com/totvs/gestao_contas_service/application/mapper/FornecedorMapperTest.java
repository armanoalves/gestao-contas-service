package com.totvs.gestao_contas_service.application.mapper;

import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FornecedorMapperTest {

    private final FornecedorMapper mapper = new FornecedorMapper();

    @Test
    void deveMapearFornecedorParaResponse() {
        var id = UUID.randomUUID();
        var agora = LocalDateTime.now();
        var fornecedor = new Fornecedor(id, "Fornecedor Teste", agora, agora);

        var response = mapper.toResponse(fornecedor);

        assertEquals(id, response.id());
        assertEquals("Fornecedor Teste", response.nome());
        assertEquals(agora, response.criadoEm());
        assertEquals(agora, response.atualizadoEm());
    }
}
