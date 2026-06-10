package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.application.dto.conta.AtualizarContaRequest;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
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
class AtualizarContaUseCaseTest {

    @Mock private ContaRepository contaRepository;
    @Mock private FornecedorRepository fornecedorRepository;
    @Mock private ContaMapper contaMapper;

    @InjectMocks private AtualizarContaUseCase useCase;

    private UUID contaId;
    private UUID fornecedorId;
    private Conta contaPendente;
    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {
        contaId = UUID.randomUUID();
        fornecedorId = UUID.randomUUID();
        fornecedor = new Fornecedor(fornecedorId, "Fornecedor", LocalDate.now().atStartOfDay(), LocalDate.now().atStartOfDay());
        contaPendente = Conta.criar(
                LocalDate.now().plusDays(30),
                new Dinheiro(new BigDecimal("500.00")),
                new Descricao("Descrição original"),
                fornecedor
        );
    }

    @Test
    void deveAtualizarConta() {
        AtualizarContaRequest request = new AtualizarContaRequest(
                LocalDate.now().plusDays(60),
                new BigDecimal("600.00"),
                "Nova descrição",
                fornecedorId
        );

        when(contaRepository.buscarPorId(contaId)).thenReturn(Optional.of(contaPendente));
        when(fornecedorRepository.buscarPorId(fornecedorId)).thenReturn(Optional.of(fornecedor));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(i -> i.getArgument(0));
        when(contaMapper.toDinheiro(any())).thenCallRealMethod();
        when(contaMapper.toDescricao(any())).thenCallRealMethod();

        useCase.executar(contaId, request);

        assertEquals(request.dataVencimento(), contaPendente.getDataVencimento());
        assertEquals(0, request.valor().compareTo(contaPendente.getValor().valor()));
        assertEquals(request.descricao(), contaPendente.getDescricao().texto());
    }

    @Test
    void deveLancarExcecaoQuandoContaNaoExiste() {
        when(contaRepository.buscarPorId(contaId)).thenReturn(Optional.empty());

        AtualizarContaRequest request = new AtualizarContaRequest(
                LocalDate.now().plusDays(60), new BigDecimal("600.00"), "Nova", fornecedorId
        );

        assertThrows(EntityNotFoundException.class, () -> useCase.executar(contaId, request));
    }

    @Test
    void deveLancarExcecaoQuandoFornecedorNaoExiste() {
        when(contaRepository.buscarPorId(contaId)).thenReturn(Optional.of(contaPendente));
        when(fornecedorRepository.buscarPorId(fornecedorId)).thenReturn(Optional.empty());

        AtualizarContaRequest request = new AtualizarContaRequest(
                LocalDate.now().plusDays(60), new BigDecimal("600.00"), "Nova", fornecedorId
        );

        assertThrows(EntityNotFoundException.class, () -> useCase.executar(contaId, request));
    }
}
