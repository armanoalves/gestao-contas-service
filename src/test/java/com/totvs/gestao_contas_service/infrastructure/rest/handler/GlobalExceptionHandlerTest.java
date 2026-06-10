package com.totvs.gestao_contas_service.infrastructure.rest.handler;

import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/contas");
    }

    @Test
    void deveTratarEntityNotFoundException() {
        var ex = new EntityNotFoundException("Conta", UUID.randomUUID());

        ResponseEntity<ErrorResponse> response = handler.handleEntityNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().status());
    }

    @Test
    void deveTratarIllegalArgumentException() {
        var ex = new IllegalArgumentException("Valor inválido");

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgument(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Valor inválido", response.getBody().message());
    }

    @Test
    void deveTratarIllegalStateException() {
        var ex = new IllegalStateException("Conta já paga");

        ResponseEntity<ErrorResponse> response = handler.handleIllegalState(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Conta já paga", response.getBody().message());
    }

    @Test
    void deveTratarExceptionGenerica() {
        var ex = new RuntimeException("Erro interno");

        ResponseEntity<ErrorResponse> response = handler.handleGeneral(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno do servidor", response.getBody().message());
    }
}
