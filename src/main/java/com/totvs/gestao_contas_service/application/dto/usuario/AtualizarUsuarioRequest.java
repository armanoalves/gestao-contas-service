package com.totvs.gestao_contas_service.application.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requisição para atualização de dados do usuário")
public record AtualizarUsuarioRequest(
        @Schema(description = "Nome do usuário", example = "João Silva Atualizado")
        @NotBlank String nome,
        @Schema(description = "Novo email do usuário", example = "joao.novo@email.com")
        @NotBlank @Email String email
) {}
