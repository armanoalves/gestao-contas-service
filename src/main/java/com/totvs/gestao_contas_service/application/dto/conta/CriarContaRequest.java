package com.totvs.gestao_contas_service.application.dto.conta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Requisição para criação de uma nova conta")
public record CriarContaRequest(
        @Schema(description = "Data de vencimento da conta", example = "2026-07-15")
        @NotNull @Future LocalDate dataVencimento,
        @Schema(description = "Valor da conta", example = "1500.50")
        @NotNull @Positive BigDecimal valor,
        @Schema(description = "Descrição da conta", example = "Fatura de energia elétrica")
        @NotBlank String descricao,
        @Schema(description = "Nome do fornecedor", example = "Companhia de Energia Elétrica")
        @NotBlank String fornecedorNome
) {}
