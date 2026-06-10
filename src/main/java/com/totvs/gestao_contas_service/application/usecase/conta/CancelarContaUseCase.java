package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.application.mapper.ContaMapper;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelarContaUseCase {
    private final ContaRepository contaRepository;
    private final ContaMapper contaMapper;

    public CancelarContaUseCase(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    @Transactional
    public ContaResponse executar(UUID id) {
        Conta conta = contaRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta", id));
        conta.cancelar();
        conta = contaRepository.salvar(conta);
        return contaMapper.toResponse(conta);
    }
}
