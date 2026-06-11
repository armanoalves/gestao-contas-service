package com.totvs.gestao_contas_service.application.dto.shared;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Relatório de contas pagas em um período")
public record RelatorioResponse(
        @Schema(description = "Data de início do período", example = "2026-01-01") LocalDate dataInicio,
        @Schema(description = "Data de fim do período", example = "2026-12-31") LocalDate dataFim,
        @Schema(description = "Valor total pago no período", example = "50000.00") BigDecimal totalPago,
        @Schema(description = "Quantidade de contas pagas no período", example = "35") long quantidadeContasPagas
) {
}
