package com.totvs.gestao_contas_service.application.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para registro de um novo usuário")
public record CriarUsuarioRequest(
        @Schema(description = "Nome completo do usuário", example = "João Silva")
        @NotBlank String nome,
        @Schema(description = "Email do usuário", example = "joao.silva@email.com")
        @NotBlank @Email String email,
        @Schema(description = "Senha do usuário (mínimo 8 caracteres)", example = "senha123")
        @NotBlank @Size(min = 8) String senha
) {}
