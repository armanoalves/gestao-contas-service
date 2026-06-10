package com.totvs.gestao_contas_service.application.usecase.fornecedor;

import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.mapper.FornecedorMapper;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarFornecedoresUseCase {
    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    public ListarFornecedoresUseCase(FornecedorRepository fornecedorRepository,
                                     FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    public List<FornecedorResponse> executar() {
        return fornecedorRepository.listarTodos()
                .stream().map(fornecedorMapper::toResponse).toList();
    }
}
