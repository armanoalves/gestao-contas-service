package com.totvs.gestao_contas_service.application.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AtualizarUsuarioRequest(
        @NotBlank String nome,
        @NotBlank @Email String email
) {}
