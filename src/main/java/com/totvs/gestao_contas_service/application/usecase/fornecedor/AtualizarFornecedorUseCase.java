package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.application.dto.fornecedor.AtualizarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.mapper.FornecedorMapper;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarFornecedorUseCase {
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    public AtualizarFornecedorUseCase(FornecedorRepository fornecedorRepository,
                                      FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    @Transactional
    public FornecedorResponse executar(UUID id, AtualizarFornecedorRequest request) {
        Fornecedor fornecedor = fornecedorRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor", id));
        fornecedor.setNome(request.nome());
        fornecedor = fornecedorRepository.salvar(fornecedor);
        return fornecedorMapper.toResponse(fornecedor);
    }
}
