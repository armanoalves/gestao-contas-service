package com.totvs.gestao_contas_service.domain.valueobject;

public record Descricao(String texto) {
    public Descricao {
        if (texto == null || texto.trim().isBlank()) {
            throw new IllegalArgumentException("A descrição da conta não pode ser vazia.");
        }
        if (texto.length() > 255) {
            throw new IllegalArgumentException("A descrição não pode conter mais de 255 caracteres.");
        }
    }
}