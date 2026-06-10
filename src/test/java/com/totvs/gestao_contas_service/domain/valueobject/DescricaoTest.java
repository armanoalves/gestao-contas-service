package com.totvs.gestao_contas_service.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescricaoTest {

    @Test
    void deveCriarDescricaoValida() {
        Descricao d = new Descricao("Pagamento de fornecedor");
        assertEquals("Pagamento de fornecedor", d.texto());
    }

    @Test
    void deveRejeitarDescricaoNula() {
        assertThrows(IllegalArgumentException.class, () -> new Descricao(null));
    }

    @Test
    void deveRejeitarDescricaoVazia() {
        assertThrows(IllegalArgumentException.class, () -> new Descricao(""));
    }

    @Test
    void deveRejeitarDescricaoComMaisDe255Caracteres() {
        String longText = "a".repeat(256);
        assertThrows(IllegalArgumentException.class, () -> new Descricao(longText));
    }

    @Test
    void deveAceitarDescricaoComExatos255Caracteres() {
        String text = "a".repeat(255);
        assertDoesNotThrow(() -> new Descricao(text));
    }
}