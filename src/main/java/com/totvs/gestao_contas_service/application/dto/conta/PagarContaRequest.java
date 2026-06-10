package com.totvs.gestao_contas_service.application.dto.conta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PagarContaRequest(@NotNull LocalDate dataPagamento) {}
