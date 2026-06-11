package com.totvs.gestao_contas_service.application.dto.shared;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Resposta paginada com lista de itens")
public record PaginacaoResponse<T>(
        @Schema(description = "Lista de itens da página atual") List<T> conteudo,
        @Schema(description = "Número da página atual (começa em 0)", example = "0") int pagina,
        @Schema(description = "Quantidade de itens por página", example = "20") int tamanho,
        @Schema(description = "Total de elementos em todas as páginas", example = "100") long totalElementos,
        @Schema(description = "Total de páginas", example = "5") int totalPaginas
) {}
