package com.totvs.gestao_contas_service.application.usecase.shared;

import com.totvs.gestao_contas_service.application.dto.shared.RelatorioResponse;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GerarRelatorioUseCaseTest {

    @Mock private ContaRepository contaRepository;

    @InjectMocks private GerarRelatorioUseCase useCase;

    @Test
    void deveGerarRelatorio() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);

        when(contaRepository.calcularTotalPagoPorPeriodo(inicio, fim))
                .thenReturn(new BigDecimal("1500.00"));
        when(contaRepository.contarPagasPorPeriodo(inicio, fim))
                .thenReturn(5L);

        RelatorioResponse response = useCase.executar(inicio, fim);

        assertEquals(inicio, response.dataInicio());
        assertEquals(fim, response.dataFim());
        assertEquals(0, new BigDecimal("1500.00").compareTo(response.totalPago()));
        assertEquals(5, response.quantidadeContasPagas());
    }

    @Test
    void deveGerarRelatorioComValorZero() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 31);

        when(contaRepository.calcularTotalPagoPorPeriodo(inicio, fim))
                .thenReturn(BigDecimal.ZERO);
        when(contaRepository.contarPagasPorPeriodo(inicio, fim))
                .thenReturn(0L);

        RelatorioResponse response = useCase.executar(inicio, fim);

        assertEquals(0, response.quantidadeContasPagas());
    }
}
