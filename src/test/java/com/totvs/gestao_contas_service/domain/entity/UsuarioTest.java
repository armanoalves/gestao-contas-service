package com.totvs.gestao_contas_service.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveCriarUsuarioValido() {
        Usuario u = new Usuario("João", "joao@email.com", "senha12345");
        assertNotNull(u.getId());
        assertEquals("João", u.getNome());
        assertEquals("joao@email.com", u.getEmail());
        assertNotNull(u.getCriadoEm());
        assertNotNull(u.getAtualizadoEm());
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(null, "joao@email.com", "senha12345"));
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario(" ", "joao@email.com", "senha12345"));
    }

    @Test
    void deveRejeitarEmailInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("João", "email-invalido", "senha12345"));
    }

    @Test
    void deveRejeitarEmailNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("João", null, "senha12345"));
    }

    @Test
    void deveRejeitarSenhaCurta() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("João", "joao@email.com", "123"));
    }

    @Test
    void deveRejeitarSenhaNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("João", "joao@email.com", null));
    }

    @Test
    void deveConverterEmailParaMinusculo() {
        Usuario u = new Usuario("João", "Joao@Email.com", "senha12345");
        assertEquals("joao@email.com", u.getEmail());
    }

    @Test
    void deveTrimNome() {
        Usuario u = new Usuario("  João  ", "joao@email.com", "senha12345");
        assertEquals("João", u.getNome());
    }

    @Test
    void deveAtualizarNome() {
        Usuario u = new Usuario("João", "joao@email.com", "senha12345");
        u.setNome("João Atualizado");
        assertEquals("João Atualizado", u.getNome());
    }

    @Test
    void deveAtualizarEmail() {
        Usuario u = new Usuario("João", "joao@email.com", "senha12345");
        u.setEmail("novo@email.com");
        assertEquals("novo@email.com", u.getEmail());
    }

    @Test
    void deveAtualizarSenha() {
        Usuario u = new Usuario("João", "joao@email.com", "senha12345");
        u.setSenha("novaSenha123");
        assertEquals("novaSenha123", u.getSenha());
    }

    @Test
    void deveCriarUsuarioComConstrutorCompleto() {
        UUID id = UUID.randomUUID();
        LocalDateTime agora = LocalDateTime.now();
        Usuario u = new Usuario(id, "João", "joao@email.com", "senha12345", agora, agora);
        assertEquals(id, u.getId());
        assertEquals("João", u.getNome());
        assertEquals("joao@email.com", u.getEmail());
        assertEquals(agora, u.getCriadoEm());
    }
}
