package com.totvs.gestao_contas_service.application.mapper;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.valueobject.Descricao;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ContaMapper {
    public ContaResponse toResponse(Conta conta) {
        return new ContaResponse(
                conta.getId(),
                conta.getDataVencimento(),
                conta.getDataPagamento(),
                conta.getSituacao(),
                conta.getValor().valor(),
                conta.getDescricao().texto(),
                conta.getFornecedor().getId(),
                conta.getFornecedor().getNome(),
                conta.getCriadoEm(),
                conta.getAtualizadoEm()
        );
    }

    public Dinheiro toDinheiro(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public Descricao toDescricao(String texto) {
        return new Descricao(texto);
    }
}
