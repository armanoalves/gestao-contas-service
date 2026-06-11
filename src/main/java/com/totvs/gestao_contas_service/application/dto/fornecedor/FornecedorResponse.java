package com.totvs.gestao_contas_service.application.dto.fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados de um fornecedor")
public record FornecedorResponse(
        @Schema(description = "ID único do fornecedor") UUID id,
        @Schema(description = "Nome do fornecedor") String nome,
        @Schema(description = "Data de criação") LocalDateTime criadoEm,
        @Schema(description = "Data da última atualização") LocalDateTime atualizadoEm
) {}
