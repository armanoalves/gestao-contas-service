package com.totvs.gestao_contas_service.application.dto.conta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Requisição para atualização de uma conta existente")
public record AtualizarContaRequest(
        @Schema(description = "Nova data de vencimento", example = "2026-08-15")
        @NotNull @Future LocalDate dataVencimento,
        @Schema(description = "Novo valor da conta", example = "1800.00")
        @NotNull @Positive BigDecimal valor,
        @Schema(description = "Nova descrição da conta", example = "Fatura de água")
        @NotBlank String descricao,
        @Schema(description = "ID do fornecedor", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @NotNull UUID fornecedorId
) {}
