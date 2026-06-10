package com.totvs.gestao_contas_service.application.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarSenhaRequest(
        @NotBlank String senhaAtual,
        @NotBlank @Size(min = 8) String novaSenha
) {}
