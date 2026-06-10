package com.totvs.gestao_contas_service.application.dto.fornecedor;

import jakarta.validation.constraints.NotBlank;

public record AtualizarFornecedorRequest(@NotBlank String nome) {
}
