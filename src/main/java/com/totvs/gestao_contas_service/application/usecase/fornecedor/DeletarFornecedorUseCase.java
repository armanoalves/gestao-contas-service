package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarFornecedorUseCase {
    private final FornecedorRepository fornecedorRepository;

    public DeletarFornecedorUseCase(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public void executar(UUID id) {
        if (fornecedorRepository.buscarPorId(id).isEmpty())
            throw new EntityNotFoundException("Fornecedor", id);
        fornecedorRepository.deletar(id);
    }
}
