package com.totvs.gestao_contas_service.application.dto.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requisição para atualização de um fornecedor")
public record AtualizarFornecedorRequest(
        @Schema(description = "Novo nome do fornecedor", example = "Fornecedor XYZ S.A.")
        @NotBlank String nome
) {
}
