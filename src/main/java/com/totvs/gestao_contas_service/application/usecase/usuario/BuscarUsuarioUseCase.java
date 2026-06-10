package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuscarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public BuscarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse executar(UUID id) {
        var usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário", id));
        return usuarioMapper.toResponse(usuario);
    }
}
