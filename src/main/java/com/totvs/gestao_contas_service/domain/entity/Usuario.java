package com.totvs.gestao_contas_service.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Usuario {
    private final UUID id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    // Construtor para novos usuários
    public Usuario(String nome, String email, String senha) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    // Construtor completo para ser usado na infra
    public Usuario(UUID id, String nome, String email, String senha,
                   LocalDateTime criadoEm, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isBlank())
            throw new IllegalArgumentException("Nome do usuário é obrigatório.");
        this.nome = nome.trim();
        this.atualizadoEm = LocalDateTime.now();
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$"))
            throw new IllegalArgumentException("E-mail inválido.");
        this.email = email.toLowerCase();
        this.atualizadoEm = LocalDateTime.now();
    }

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 8)
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres.");
        this.senha = senha;
        this.atualizadoEm = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
}
