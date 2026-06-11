package com.totvs.gestao_contas_service.application.dto.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requisição para cadastro de um novo fornecedor")
public record CriarFornecedorRequest(
        @Schema(description = "Nome do fornecedor", example = "Fornecedor ABC Ltda")
        @NotBlank String nome
) {}
