package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.application.dto.fornecedor.AtualizarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.mapper.FornecedorMapper;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarFornecedorUseCaseTest {

    @Mock private FornecedorRepository fornecedorRepository;
    @Mock private FornecedorMapper fornecedorMapper;

    @InjectMocks private AtualizarFornecedorUseCase atualizarFornecedorUseCase;

    @Test
    void deveAtualizarFornecedor() {
        var id = UUID.randomUUID();
        var request = new AtualizarFornecedorRequest("Nome Atualizado");
        var fornecedorExistente = new Fornecedor(id, "Nome Antigo",
                LocalDateTime.now(), LocalDateTime.now());

        when(fornecedorRepository.buscarPorId(id)).thenReturn(Optional.of(fornecedorExistente));
        when(fornecedorRepository.salvar(any(Fornecedor.class))).thenReturn(fornecedorExistente);
        when(fornecedorMapper.toResponse(fornecedorExistente))
                .thenReturn(new FornecedorResponse(id, "Nome Atualizado",
                        fornecedorExistente.getCriadoEm(), LocalDateTime.now()));

        var response = atualizarFornecedorUseCase.executar(id, request);

        assertEquals("Nome Atualizado", response.nome());
        assertEquals("Nome Atualizado", fornecedorExistente.getNome());
        verify(fornecedorRepository).salvar(fornecedorExistente);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        var id = UUID.randomUUID();
        var request = new AtualizarFornecedorRequest("Nome");

        when(fornecedorRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> atualizarFornecedorUseCase.executar(id, request));
        verify(fornecedorRepository, never()).salvar(any());
    }
}
