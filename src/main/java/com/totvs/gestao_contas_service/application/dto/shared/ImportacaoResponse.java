package com.totvs.gestao_contas_service.application.dto.shared;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta da solicitação de importação CSV")
public record ImportacaoResponse(
        @Schema(description = "ID do protocolo para rastreamento da importação") UUID protocoloId,
        @Schema(description = "Mensagem de status da importação") String mensagem
) {}
