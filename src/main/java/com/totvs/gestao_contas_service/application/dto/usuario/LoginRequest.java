package com.totvs.gestao_contas_service.application.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requisição de login/autenticação")
public record LoginRequest(
        @Schema(description = "Email do usuário", example = "joao.silva@email.com")
        @NotBlank @Email String email,
        @Schema(description = "Senha do usuário", example = "senha123")
        @NotBlank String senha
) {}
