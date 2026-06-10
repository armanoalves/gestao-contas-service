package com.totvs.gestao_contas_service.application.dto.conta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarContaRequest(
        @NotNull @Future LocalDate dataVencimento,
        @NotNull @Positive BigDecimal valor,
        @NotBlank String descricao,
        @NotBlank String fornecedorNome
) {}
