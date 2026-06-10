package com.totvs.gestao_contas_service.application.usecase.shared;

import com.totvs.gestao_contas_service.application.dto.shared.RelatorioResponse;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GerarRelatorioUseCase {
    private final ContaRepository contaRepository;

    public GerarRelatorioUseCase(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public RelatorioResponse executar(LocalDate inicio, LocalDate fim) {
        Dinheiro total = contaRepository.calcularTotalPagoPorPeriodo(inicio, fim);
        long quantidade = contaRepository.contarPagasPorPeriodo(inicio, fim);
        return new RelatorioResponse(inicio, fim, total.valor(), quantidade);
    }
}
