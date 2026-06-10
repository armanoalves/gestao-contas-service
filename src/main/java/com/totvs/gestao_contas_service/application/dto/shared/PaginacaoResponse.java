package com.totvs.gestao_contas_service.application.dto.shared;

import java.util.List;

public record PaginacaoResponse<T>(
        List<T> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas
) {}
