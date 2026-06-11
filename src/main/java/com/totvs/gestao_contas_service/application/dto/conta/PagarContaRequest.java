package com.totvs.gestao_contas_service.application.dto.conta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Requisição para pagamento de uma conta")
public record PagarContaRequest(
        @Schema(description = "Data em que o pagamento foi realizado", example = "2026-06-10")
        @NotNull LocalDate dataPagamento
) {}
