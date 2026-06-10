package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarContaUseCase {
    private final ContaRepository contaRepository;
    private final ContaMapper contaMapper;

    public BuscarContaUseCase(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    public ContaResponse executar(UUID id) {
        Conta conta = contaRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta", id));
        return contaMapper.toResponse(conta);
    }
}
