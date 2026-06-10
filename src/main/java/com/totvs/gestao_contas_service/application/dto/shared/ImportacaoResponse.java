package com.totvs.gestao_contas_service.application.dto.shared;

import java.util.UUID;

public record ImportacaoResponse(
        UUID protocoloId,
        String mensagem
) {}
