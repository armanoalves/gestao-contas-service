package com.totvs.gestao_contas_service.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Situação de uma conta: PENDENTE, PAGO ou CANCELADO")
public enum Situacao {
    PENDENTE,
    PAGO,
    CANCELADO
}
