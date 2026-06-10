package com.totvs.gestao_contas_service.application.usecase.shared;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.dto.shared.PaginacaoResponse;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ListarContasUseCase {
    private final ContaRepository contaRepository;
    private final ContaMapper contaMapper;

    public ListarContasUseCase(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    public PaginacaoResponse<ContaResponse> executar(int pagina, int tamanho,
                                                     LocalDate dataVencimento, String descricao) {
        long total = contaRepository.contar(dataVencimento, descricao);
        var contas = contaRepository.listarPaginada(pagina, tamanho, dataVencimento, descricao);
        var responses = contas.stream().map(contaMapper::toResponse).toList();
        int totalPaginas = (int) Math.ceil((double) total / tamanho);
        return new PaginacaoResponse<>(responses, pagina, tamanho, total, totalPaginas);
    }
}
