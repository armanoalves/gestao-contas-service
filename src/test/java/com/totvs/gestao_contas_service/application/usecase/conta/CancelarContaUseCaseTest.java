package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.entity.Situacao;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.valueobject.Descricao;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarContaUseCaseTest {

    @Mock private ContaRepository contaRepository;
    @Mock private ContaMapper contaMapper;

    @InjectMocks private CancelarContaUseCase useCase;

    private UUID contaId;
    private Conta contaPendente;

    @BeforeEach
    void setUp() {
        contaId = UUID.randomUUID();
        Fornecedor fornecedor = new Fornecedor("Fornecedor");
        contaPendente = Conta.criar(
                LocalDate.now().plusDays(30),
                new Dinheiro(new BigDecimal("500.00")),
                new Descricao("Descrição"),
                fornecedor
        );
    }

    @Test
    void deveCancelarConta() {
        when(contaRepository.buscarPorId(contaId)).thenReturn(Optional.of(contaPendente));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(i -> i.getArgument(0));
        when(contaMapper.toResponse(any(Conta.class))).thenReturn(
                new ContaResponse(null, null, null, Situacao.CANCELADO, null, null, null, null, null, null)
        );

        ContaResponse response = useCase.executar(contaId);

        assertEquals(Situacao.CANCELADO, response.situacao());
        verify(contaRepository).salvar(contaPendente);
    }

    @Test
    void deveLancarExcecaoQuandoContaNaoExiste() {
        when(contaRepository.buscarPorId(contaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> useCase.executar(contaId));
    }
}
