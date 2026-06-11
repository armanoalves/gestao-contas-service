package com.totvs.gestao_contas_service.application.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de autenticação com token JWT")
public record LoginResponse(
        @Schema(description = "Token JWT para autenticação nas demais requisições") String token,
        @Schema(description = "Tipo do token (Bearer)", example = "Bearer") String tipo
) {
    public LoginResponse(String token) {
        this(token, "Bearer");
    }
}
