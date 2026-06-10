package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.dto.conta.CriarContaRequest;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CriarContaUseCase {
    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ContaMapper contaMapper;

    public CriarContaUseCase(ContaRepository contaRepository,
                             FornecedorRepository fornecedorRepository,
                             ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.contaMapper = contaMapper;
    }

    @Transactional
    public ContaResponse executar(CriarContaRequest request) {
        Fornecedor fornecedor = fornecedorRepository.buscarPorNome(request.fornecedorNome())
                .orElseGet(() -> new Fornecedor(request.fornecedorNome()));
        fornecedor = fornecedorRepository.salvar(fornecedor);
        Conta conta = Conta.criar(
                request.dataVencimento(),
                contaMapper.toDinheiro(request.valor()),
                contaMapper.toDescricao(request.descricao()),
                fornecedor
        );
        conta = contaRepository.salvar(conta);
        return contaMapper.toResponse(conta);
    }
}
