package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.AtualizarUsuarioRequest;
import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public AtualizarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioResponse executar(UUID id, AtualizarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário", id));
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario = usuarioRepository.salvar(usuario);
        return usuarioMapper.toResponse(usuario);
    }
}
