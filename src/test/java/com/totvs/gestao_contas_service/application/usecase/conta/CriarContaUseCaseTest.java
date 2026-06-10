package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.application.dto.conta.CriarContaRequest;
import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarContaUseCaseTest {

    @Mock private ContaRepository contaRepository;
    @Mock private FornecedorRepository fornecedorRepository;
    @Mock private ContaMapper contaMapper;

    @InjectMocks private CriarContaUseCase useCase;

    private CriarContaRequest request;

    @BeforeEach
    void setUp() {
        request = new CriarContaRequest(
                LocalDate.now().plusDays(30),
                new BigDecimal("500.00"),
                "Serviço de consultoria",
                "Fornecedor Teste"
        );
    }

    @Test
    void deveCriarContaComNovoFornecedor() {
        when(contaMapper.toDinheiro(any())).thenCallRealMethod();
        when(contaMapper.toDescricao(any())).thenCallRealMethod();
        when(fornecedorRepository.buscarPorNome("Fornecedor Teste")).thenReturn(Optional.empty());
        when(fornecedorRepository.salvar(any(Fornecedor.class))).thenAnswer(i -> i.getArgument(0));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(i -> i.getArgument(0));
        when(contaMapper.toResponse(any(Conta.class))).thenReturn(
                new ContaResponse(null, null, null, null, null, null, null, null, null, null)
        );

        useCase.executar(request);

        verify(fornecedorRepository).buscarPorNome("Fornecedor Teste");
        verify(fornecedorRepository).salvar(any(Fornecedor.class));
        verify(contaRepository).salvar(any(Conta.class));
        verify(contaMapper).toResponse(any(Conta.class));
    }

    @Test
    void deveCriarContaComFornecedorExistente() {
        when(contaMapper.toDinheiro(any())).thenCallRealMethod();
        when(contaMapper.toDescricao(any())).thenCallRealMethod();
        Fornecedor fornecedorExistente = new Fornecedor("Fornecedor Teste");
        when(fornecedorRepository.buscarPorNome("Fornecedor Teste")).thenReturn(Optional.of(fornecedorExistente));
        when(fornecedorRepository.salvar(any(Fornecedor.class))).thenAnswer(i -> i.getArgument(0));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(i -> i.getArgument(0));
        when(contaMapper.toResponse(any(Conta.class))).thenReturn(
                new ContaResponse(null, null, null, null, null, null, null, null, null, null)
        );

        useCase.executar(request);

        verify(fornecedorRepository).buscarPorNome("Fornecedor Teste");
        verify(fornecedorRepository, times(1)).salvar(any(Fornecedor.class));
    }

    @Test
    void devePassarDadosCorretosParaCriacao() {
        when(fornecedorRepository.buscarPorNome(anyString())).thenReturn(Optional.empty());
        when(fornecedorRepository.salvar(any(Fornecedor.class))).thenAnswer(i -> i.getArgument(0));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(i -> i.getArgument(0));
        when(contaMapper.toDinheiro(any())).thenCallRealMethod();
        when(contaMapper.toDescricao(any())).thenCallRealMethod();

        useCase.executar(request);

        ArgumentCaptor<Conta> captor = ArgumentCaptor.forClass(Conta.class);
        verify(contaRepository).salvar(captor.capture());
        Conta contaSalva = captor.getValue();

        assertEquals(request.dataVencimento(), contaSalva.getDataVencimento());
        assertEquals(0, request.valor().compareTo(contaSalva.getValor().valor()));
        assertEquals(request.descricao(), contaSalva.getDescricao().texto());
    }
}
