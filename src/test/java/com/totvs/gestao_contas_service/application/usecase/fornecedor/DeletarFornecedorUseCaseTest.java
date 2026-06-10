package com.totvs.gestao_contas_service.application.usecase.fornecedor;

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
class DeletarFornecedorUseCaseTest {

    @Mock private FornecedorRepository fornecedorRepository;

    @InjectMocks private DeletarFornecedorUseCase deletarFornecedorUseCase;

    @Test
    void deveDeletarFornecedor() {
        var id = UUID.randomUUID();

        when(fornecedorRepository.buscarPorId(id))
                .thenReturn(Optional.of(new Fornecedor(id, "Nome",
                        LocalDateTime.now(), LocalDateTime.now())));

        deletarFornecedorUseCase.executar(id);

        verify(fornecedorRepository).deletar(id);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        var id = UUID.randomUUID();

        when(fornecedorRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> deletarFornecedorUseCase.executar(id));
        verify(fornecedorRepository, never()).deletar(any());
    }
}
