package com.totvs.gestao_contas_service.application.dto.usuario;

public record LoginResponse(String token, String tipo) {
    public LoginResponse(String token) {
        this(token, "Bearer");
    }
}
