package com.totvs.gestao_contas_service.application.dto.fornecedor;

import java.time.LocalDateTime;
import java.util.UUID;

public record FornecedorResponse(
        UUID id,
        String nome,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}
