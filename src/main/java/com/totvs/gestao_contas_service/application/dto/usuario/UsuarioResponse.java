package com.totvs.gestao_contas_service.application.dto.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}
