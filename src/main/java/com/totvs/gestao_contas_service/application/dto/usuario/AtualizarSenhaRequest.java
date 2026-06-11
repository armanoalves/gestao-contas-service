package com.totvs.gestao_contas_service.application.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para alteração de senha")
public record AtualizarSenhaRequest(
        @Schema(description = "Senha atual do usuário", example = "senha123")
        @NotBlank String senhaAtual,
        @Schema(description = "Nova senha (mínimo 8 caracteres)", example = "novaSenha456")
        @NotBlank @Size(min = 8) String novaSenha
) {}
