package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.LoginRequest;
import com.totvs.gestao_contas_service.application.dto.usuario.LoginResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import com.totvs.gestao_contas_service.infrastructure.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AutenticarUsuarioUseCase(UsuarioRepository usuarioRepository,
                                    JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse executar(LoginRequest request) {
        Usuario usuario = usuarioRepository.buscarPorEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("E-mail inválido.."));
        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Senha inválida.");
        }
        String token = jwtUtil.gerarToken(usuario.getId(), usuario.getEmail());
        return new LoginResponse(token);
    }
}
