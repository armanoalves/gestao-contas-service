package com.totvs.gestao_contas_service.domain.exception;

public class EntityNotFoundException extends DomainException {

    public EntityNotFoundException(String entity, Object id) {
        super("%s não encontrado(a): %s".formatted(entity, id));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
