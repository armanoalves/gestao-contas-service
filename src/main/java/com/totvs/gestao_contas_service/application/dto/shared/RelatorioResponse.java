package com.totvs.gestao_contas_service.application.dto.shared;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RelatorioResponse(
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal totalPago,
        long quantidadeContasPagas
) {
}
