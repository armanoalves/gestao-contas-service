package com.totvs.gestao_contas_service.infrastructure.rest.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Resposta padronizada de erro da API")
public record ErrorResponse(
        @Schema(description = "Código HTTP do erro", example = "400") int status,
        @Schema(description = "Descrição do status HTTP", example = "Bad Request") String error,
        @Schema(description = "Mensagem descritiva do erro") String message,
        @Schema(description = "Timestamp do erro no formato ISO") String timestamp,
        @Schema(description = "Caminho da requisição que gerou o erro") String path,
        @Schema(description = "Erros de validação por campo (quando aplicável)") List<FieldError> errors
) {

    @Schema(description = "Erro de validação de um campo específico")
    public record FieldError(
            @Schema(description = "Nome do campo com erro") String field,
            @Schema(description = "Mensagem de erro do campo") String message
    ) {}

    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                path,
                null
        );
    }

    public static ErrorResponse of(HttpStatus status, String message, String path, List<FieldError> errors) {
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                path,
                errors
        );
    }
}
