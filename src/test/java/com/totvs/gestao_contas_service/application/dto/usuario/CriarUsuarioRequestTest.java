package com.totvs.gestao_contas_service.application.dto.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriarUsuarioRequestTest {

    @Test
    void deveCriarRequest() {
        var request = new CriarUsuarioRequest("Maria", "maria@email.com", "senha12345");

        assertEquals("Maria", request.nome());
        assertEquals("maria@email.com", request.email());
        assertEquals("senha12345", request.senha());
    }

    @Test
    void deveAceitarEmailComMaiusculas() {
        var request = new CriarUsuarioRequest("João", "Joao@Email.com", "senha12345");
        assertEquals("Joao@Email.com", request.email());
    }
}
