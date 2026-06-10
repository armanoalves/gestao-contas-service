package com.totvs.gestao_contas_service.application.dto.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtualizarSenhaRequestTest {

    @Test
    void deveCriarRequest() {
        var request = new AtualizarSenhaRequest("senhaAntiga", "novaSenha123");

        assertEquals("senhaAntiga", request.senhaAtual());
        assertEquals("novaSenha123", request.novaSenha());
    }
}
