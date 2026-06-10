package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.application.dto.fornecedor.CriarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.mapper.FornecedorMapper;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarFornecedorUseCaseTest {

    @Mock private FornecedorRepository fornecedorRepository;
    @Mock private FornecedorMapper fornecedorMapper;

    @InjectMocks private CriarFornecedorUseCase criarFornecedorUseCase;

    @Test
    void deveCriarFornecedor() {
        var request = new CriarFornecedorRequest("Fornecedor Teste");
        var fornecedorSalvo = new Fornecedor(UUID.randomUUID(), "Fornecedor Teste",
                LocalDateTime.now(), LocalDateTime.now());

        when(fornecedorRepository.salvar(any(Fornecedor.class))).thenReturn(fornecedorSalvo);
        when(fornecedorMapper.toResponse(fornecedorSalvo))
                .thenReturn(new FornecedorResponse(fornecedorSalvo.getId(), fornecedorSalvo.getNome(),
                        fornecedorSalvo.getCriadoEm(), fornecedorSalvo.getAtualizadoEm()));

        var response = criarFornecedorUseCase.executar(request);

        assertEquals("Fornecedor Teste", response.nome());
        assertNotNull(response.id());

        var captor = ArgumentCaptor.forClass(Fornecedor.class);
        verify(fornecedorRepository).salvar(captor.capture());
        assertEquals("Fornecedor Teste", captor.getValue().getNome());
    }
}
