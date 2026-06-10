package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.CriarUsuarioRequest;
import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CriarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder  passwordEncoder;

    public CriarUsuarioUseCase(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper,
                               PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse executar(CriarUsuarioRequest request) {
        if (usuarioRepository.buscarPorEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        String senhaHash = passwordEncoder.encode(request.senha());
        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                senhaHash
        );
        usuario = usuarioRepository.salvar(usuario);
        return usuarioMapper.toResponse(usuario);
    }
}
