package com.totvs.gestao_contas_service.application.usecase.conta;

import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarContaUseCase {
    private final ContaRepository contaRepository;

    public DeletarContaUseCase(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public void executar(UUID id) {
        if (contaRepository.buscarPorId(id).isEmpty())
            throw new EntityNotFoundException("Conta", id);
        contaRepository.deletar(id);
    }
}
