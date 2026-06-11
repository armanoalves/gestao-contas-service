package com.totvs.gestao_contas_service.application.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados de um usuário")
public record UsuarioResponse(
        @Schema(description = "ID único do usuário") UUID id,
        @Schema(description = "Nome do usuário") String nome,
        @Schema(description = "Email do usuário") String email,
        @Schema(description = "Data de criação") LocalDateTime criadoEm,
        @Schema(description = "Data da última atualização") LocalDateTime atualizadoEm
) {}
