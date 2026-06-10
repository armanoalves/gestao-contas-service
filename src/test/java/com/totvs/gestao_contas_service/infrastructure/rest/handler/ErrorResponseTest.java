package com.totvs.gestao_contas_service.infrastructure.rest.handler;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void deveCriarErrorResponseViaOf() {
        var response = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                "/api/contas"
        );

        assertEquals(400, response.status());
        assertEquals("Bad Request", response.error());
        assertEquals("Erro de validação", response.message());
        assertEquals("/api/contas", response.path());
        assertNotNull(response.timestamp());
        assertNull(response.errors());
    }

    @Test
    void deveCriarErrorResponseComFieldErrors() {
        var fieldErrors = List.of(
                new ErrorResponse.FieldError("nome", "não pode estar em branco")
        );
        var response = ErrorResponse.of(
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Erro de validação",
                "/api/fornecedores",
                fieldErrors
        );

        assertEquals(422, response.status());
        assertEquals(1, response.errors().size());
        assertEquals("nome", response.errors().getFirst().field());
        assertEquals("não pode estar em branco", response.errors().getFirst().message());
    }

    @Test
    void fieldErrorDeveCriar() {
        var fieldError = new ErrorResponse.FieldError("email", "formato inválido");

        assertEquals("email", fieldError.field());
        assertEquals("formato inválido", fieldError.message());
    }
}
