package com.totvs.gestao_contas_service.application.usecase.fornecedor;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarFornecedorUseCaseTest {

    @Mock private FornecedorRepository fornecedorRepository;
    @Mock private FornecedorMapper fornecedorMapper;

    @InjectMocks private BuscarFornecedorUseCase buscarFornecedorUseCase;

    @Test
    void deveBuscarFornecedor() {
        var id = UUID.randomUUID();
        var fornecedor = new Fornecedor(id, "Fornecedor", LocalDateTime.now(), LocalDateTime.now());
        var response = new FornecedorResponse(id, "Fornecedor",
                fornecedor.getCriadoEm(), fornecedor.getAtualizadoEm());

        when(fornecedorRepository.buscarPorId(id)).thenReturn(Optional.of(fornecedor));
        when(fornecedorMapper.toResponse(fornecedor)).thenReturn(response);

        var result = buscarFornecedorUseCase.executar(id);

        assertEquals("Fornecedor", result.nome());
        assertEquals(id, result.id());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        var id = UUID.randomUUID();

        when(fornecedorRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> buscarFornecedorUseCase.executar(id));
    }
}
