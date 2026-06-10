package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarUsuariosUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public ListarUsuariosUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioResponse> executar() {
        return usuarioRepository.listarTodos()
                .stream().map(usuarioMapper::toResponse).toList();
    }
}
