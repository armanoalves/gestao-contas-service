package com.totvs.gestao_contas_service.infrastructure.rest.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        int status,
        String error,
        String message,
        String timestamp,
        String path,
        List<FieldError> errors
) {

    public record FieldError(String field, String message) {}

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
