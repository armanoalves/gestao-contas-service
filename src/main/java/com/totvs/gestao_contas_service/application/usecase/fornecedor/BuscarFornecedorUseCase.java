package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.mapper.FornecedorMapper;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarFornecedorUseCase {
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    public BuscarFornecedorUseCase(FornecedorRepository fornecedorRepository,
                                   FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    public FornecedorResponse executar(UUID id) {
        var fornecedor = fornecedorRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor", id));
        return fornecedorMapper.toResponse(fornecedor);
    }
}
