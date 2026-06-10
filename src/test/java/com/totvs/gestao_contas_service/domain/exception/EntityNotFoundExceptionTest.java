package com.totvs.gestao_contas_service.domain.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EntityNotFoundExceptionTest {

    @Test
    void deveCriarComEntidadeEId() {
        var id = UUID.randomUUID();
        var ex = new EntityNotFoundException("Conta", id);

        assertEquals("Conta não encontrado(a): " + id, ex.getMessage());
    }

    @Test
    void deveCriarComMensagemPersonalizada() {
        var ex = new EntityNotFoundException("Nenhum fornecedor encontrado.");

        assertEquals("Nenhum fornecedor encontrado.", ex.getMessage());
    }

    @Test
    void deveSerSubclasseDeDomainException() {
        var ex = new EntityNotFoundException("Teste", UUID.randomUUID());

        assertInstanceOf(DomainException.class, ex);
        assertInstanceOf(RuntimeException.class, ex);
    }
}
