package com.totvs.gestao_contas_service.domain.valueobject;

import java.math.BigDecimal;

public record Dinheiro(BigDecimal valor) {
    public Dinheiro {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da conta deve ser maior que zero.");
        }
    }

    public Dinheiro somar(Dinheiro outro) {
        return new Dinheiro(this.valor.add(outro.valor));
    }
}
