package com.totvs.gestao_contas_service.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DinheiroTest {

    @Test
    void deveCriarDinheiroComValorValido() {
        Dinheiro d = new Dinheiro(new BigDecimal("100.50"));
        assertEquals(0, new BigDecimal("100.50").compareTo(d.valor()));
    }

    @Test
    void deveRejeitarValorNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Dinheiro(null));
    }

    @Test
    void deveRejeitarValorZero() {
        assertThrows(IllegalArgumentException.class, () -> new Dinheiro(BigDecimal.ZERO));
    }

    @Test
    void deveRejeitarValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Dinheiro(new BigDecimal("-10")));
    }

    @Test
    void deveSomarDoisValores() {
        Dinheiro a = new Dinheiro(new BigDecimal("100"));
        Dinheiro b = new Dinheiro(new BigDecimal("50"));
        Dinheiro resultado = a.somar(b);
        assertEquals(0, new BigDecimal("150").compareTo(resultado.valor()));
    }
}