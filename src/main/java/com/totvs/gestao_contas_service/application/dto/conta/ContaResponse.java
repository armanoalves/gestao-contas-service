package com.totvs.gestao_contas_service.application.dto.conta;

import com.totvs.gestao_contas_service.domain.entity.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados de uma conta a pagar")
public record ContaResponse(
        @Schema(description = "ID único da conta") UUID id,
        @Schema(description = "Data de vencimento", example = "2026-07-15") LocalDate dataVencimento,
        @Schema(description = "Data de pagamento", example = "2026-07-10") LocalDate dataPagamento,
        @Schema(description = "Situação atual da conta") Situacao situacao,
        @Schema(description = "Valor da conta", example = "1500.50") BigDecimal valor,
        @Schema(description = "Descrição da conta") String descricao,
        @Schema(description = "ID do fornecedor") UUID fornecedorId,
        @Schema(description = "Nome do fornecedor") String fornecedorNome,
        @Schema(description = "Data de criação") LocalDateTime criadoEm,
        @Schema(description = "Data da última atualização") LocalDateTime atualizadoEm
) {}
