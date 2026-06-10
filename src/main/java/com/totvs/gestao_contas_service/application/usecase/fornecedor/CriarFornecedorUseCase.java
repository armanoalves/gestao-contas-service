package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.application.dto.fornecedor.CriarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.mapper.FornecedorMapper;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CriarFornecedorUseCase {
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    public CriarFornecedorUseCase(FornecedorRepository fornecedorRepository,
                                  FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    @Transactional
    public FornecedorResponse executar(CriarFornecedorRequest request) {
        Fornecedor fornecedor = new Fornecedor(request.nome());
        fornecedor = fornecedorRepository.salvar(fornecedor);
        return fornecedorMapper.toResponse(fornecedor);
    }
}
