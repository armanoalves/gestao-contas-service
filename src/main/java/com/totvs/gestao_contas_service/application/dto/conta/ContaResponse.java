package com.totvs.gestao_contas_service.application.dto.conta;

import com.totvs.gestao_contas_service.domain.entity.Situacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ContaResponse(
        UUID id,
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        Situacao situacao,
        BigDecimal valor,
        String descricao,
        UUID fornecedorId,
        String fornecedorNome,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}
