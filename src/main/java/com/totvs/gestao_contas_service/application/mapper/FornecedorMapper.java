package com.totvs.gestao_contas_service.application.mapper;

import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import org.springframework.stereotype.Component;

@Component
public class FornecedorMapper {
    public FornecedorResponse toResponse(Fornecedor fornecedor) {
        return new FornecedorResponse(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getCriadoEm(),
                fornecedor.getAtualizadoEm()
        );
    }
}
