package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void executar(UUID id) {
        if(usuarioRepository.buscarPorId(id).isEmpty())
            throw new EntityNotFoundException("Usuário", id);
        usuarioRepository.deletar(id);
    }
}
