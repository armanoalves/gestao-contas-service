package com.totvs.gestao_contas_service.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Fornecedor {
    private final UUID id;
    private String nome;
    private final LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public Fornecedor(String nome) {
        this.id = UUID.randomUUID();
        setNome(nome);
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    public Fornecedor(UUID id, String nome,
                      LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        setNome(nome);
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isBlank())
            throw new IllegalArgumentException("Nome do fornecedor é obrigatório.");
        this.nome = nome.trim();
        this.atualizadoEm = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }

}