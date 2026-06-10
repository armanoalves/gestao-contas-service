package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.application.dto.conta.AtualizarContaRequest;
import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarContaUseCase {
    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ContaMapper contaMapper;

    public AtualizarContaUseCase(ContaRepository contaRepository,
                                 FornecedorRepository fornecedorRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.contaMapper = contaMapper;
    }

    @Transactional
    public ContaResponse executar(UUID id, AtualizarContaRequest request) {
        Conta conta = contaRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta", id));
        Fornecedor fornecedor = fornecedorRepository.buscarPorId(request.fornecedorId())
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor", id));
        conta.atualizarDados(
                request.dataVencimento(),
                contaMapper.toDinheiro(request.valor()),
                contaMapper.toDescricao(request.descricao()),
                fornecedor
        );
        conta = contaRepository.salvar(conta);
        return contaMapper.toResponse(conta);
    }
}
