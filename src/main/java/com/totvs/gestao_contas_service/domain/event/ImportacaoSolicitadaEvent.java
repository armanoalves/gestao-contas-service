package com.totvs.gestao_contas_service.domain.event;

import java.util.UUID;

public record ImportacaoSolicitadaEvent(UUID protocolo, byte[] conteudoCsv) {}
