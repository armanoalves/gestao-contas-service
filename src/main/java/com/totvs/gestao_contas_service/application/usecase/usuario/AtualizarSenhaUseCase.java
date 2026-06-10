package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.AtualizarSenhaRequest;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarSenhaUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AtualizarSenhaUseCase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void executar(UUID id, AtualizarSenhaRequest request) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário", id));
        if (!passwordEncoder.matches(request.senhaAtual(), usuario.getSenha()))
            throw new IllegalArgumentException("Senha atual inválida!");
        usuario.setSenha(passwordEncoder.encode(request.novaSenha()));
        usuarioRepository.salvar(usuario);
    }
}
