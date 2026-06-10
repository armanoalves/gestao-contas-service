package com.totvs.gestao_contas_service.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FornecedorTest {

    @Test
    void deveCriarFornecedorValido() {
        Fornecedor f = new Fornecedor("Fornecedor Teste");
        assertNotNull(f.getId());
        assertEquals("Fornecedor Teste", f.getNome());
        assertNotNull(f.getCriadoEm());
        assertNotNull(f.getAtualizadoEm());
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Fornecedor(null));
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Fornecedor(" "));
    }

    @Test
    void deveAtualizarNome() {
        Fornecedor f = new Fornecedor("Nome Antigo");
        f.setNome("Nome Novo");
        assertEquals("Nome Novo", f.getNome());
    }

    @Test
    void deveRejeitarAtualizarNomeNulo() {
        Fornecedor f = new Fornecedor("Nome Antigo");
        assertThrows(IllegalArgumentException.class, () -> f.setNome(null));
    }

    @Test
    void deveRejeitarAtualizarNomeVazio() {
        Fornecedor f = new Fornecedor("Nome Antigo");
        assertThrows(IllegalArgumentException.class, () -> f.setNome(" "));
    }

    @Test
    void deveCriarFornecedorComConstrutorCompleto() {
        var id = java.util.UUID.randomUUID();
        var criadoEm = java.time.LocalDateTime.now();
        Fornecedor f = new Fornecedor(id, "Fornecedor Teste", criadoEm, criadoEm);
        assertEquals(id, f.getId());
        assertEquals("Fornecedor Teste", f.getNome());
        assertEquals(criadoEm, f.getCriadoEm());
    }
}
