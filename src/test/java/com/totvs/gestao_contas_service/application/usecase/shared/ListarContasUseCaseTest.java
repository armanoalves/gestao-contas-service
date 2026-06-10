package com.totvs.gestao_contas_service.application.usecase.shared;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.dto.shared.PaginacaoResponse;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.valueobject.Descricao;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarContasUseCaseTest {

    @Mock private ContaRepository contaRepository;
    @Mock private ContaMapper contaMapper;

    @InjectMocks private ListarContasUseCase useCase;

    @Test
    void deveListarContasPaginadas() {
        Fornecedor fornecedor = new Fornecedor("Fornecedor");
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), new Dinheiro(new BigDecimal("500.00")), new Descricao("Descrição"), fornecedor);

        when(contaRepository.contar(null, null)).thenReturn(1L);
        when(contaRepository.listarPaginada(0, 20, null, null)).thenReturn(List.of(conta));
        when(contaMapper.toResponse(conta)).thenReturn(
                new ContaResponse(
                        conta.getId(), null, null, null, null, null, null, null, null, null
                )
        );

        PaginacaoResponse<ContaResponse> result =
                useCase.executar(0, 20, null, null);

        assertEquals(1, result.totalElementos());
        assertEquals(1, result.totalPaginas());
        assertEquals(1, result.conteudo().size());
        assertEquals(conta.getId(), result.conteudo().getFirst().id());
    }

    @Test
    void deveListarContasComFiltros() {
        LocalDate dataFiltro = LocalDate.now().plusDays(30);

        when(contaRepository.contar(dataFiltro, "consulta")).thenReturn(0L);
        when(contaRepository.listarPaginada(0, 10, dataFiltro, "consulta")).thenReturn(List.of());

        PaginacaoResponse<ContaResponse> result =
                useCase.executar(0, 10, dataFiltro, "consulta");

        assertEquals(0, result.totalElementos());
        assertTrue(result.conteudo().isEmpty());
    }
}
