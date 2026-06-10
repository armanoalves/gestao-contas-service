package com.totvs.gestao_contas_service.application.dto.shared;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RelatorioResponseTest {

    @Test
    void deveCriarResponse() {
        var inicio = LocalDate.of(2026, 1, 1);
        var fim = LocalDate.of(2026, 12, 31);
        var response = new RelatorioResponse(inicio, fim, BigDecimal.valueOf(5000), 10);

        assertEquals(inicio, response.dataInicio());
        assertEquals(fim, response.dataFim());
        assertEquals(BigDecimal.valueOf(5000), response.totalPago());
        assertEquals(10, response.quantidadeContasPagas());
    }
}
