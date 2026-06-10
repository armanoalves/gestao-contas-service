package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.mapper.FornecedorMapper;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarFornecedoresUseCaseTest {

    @Mock private FornecedorRepository fornecedorRepository;
    @Mock private FornecedorMapper fornecedorMapper;

    @InjectMocks private ListarFornecedoresUseCase listarFornecedoresUseCase;

    @Test
    void deveListarFornecedores() {
        var fornecedor = new Fornecedor(UUID.randomUUID(), "Fornecedor A",
                LocalDateTime.now(), LocalDateTime.now());
        var response = new FornecedorResponse(fornecedor.getId(), fornecedor.getNome(),
                fornecedor.getCriadoEm(), fornecedor.getAtualizadoEm());

        when(fornecedorRepository.listarTodos()).thenReturn(List.of(fornecedor));
        when(fornecedorMapper.toResponse(fornecedor)).thenReturn(response);

        var resultados = listarFornecedoresUseCase.executar();

        assertEquals(1, resultados.size());
        assertEquals("Fornecedor A", resultados.getFirst().nome());
    }

    @Test
    void deveRetornarListaVazia() {
        when(fornecedorRepository.listarTodos()).thenReturn(List.of());

        var resultados = listarFornecedoresUseCase.executar();

        assertTrue(resultados.isEmpty());
    }
}
