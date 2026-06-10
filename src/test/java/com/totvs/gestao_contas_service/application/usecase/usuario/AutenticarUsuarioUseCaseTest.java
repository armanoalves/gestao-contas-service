package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.LoginRequest;
import com.totvs.gestao_contas_service.application.dto.usuario.LoginResponse;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import com.totvs.gestao_contas_service.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticarUsuarioUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private JwtUtil jwtUtil;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private AutenticarUsuarioUseCase useCase;

    @Test
    void deveAutenticarComCredenciaisValidas() {
        UUID usuarioId = UUID.randomUUID();
        Usuario usuario = new Usuario(usuarioId, "João", "joao@email.com", "hash-da-senha", null, null);
        LoginRequest request = new LoginRequest("joao@email.com", "senha12345");

        when(usuarioRepository.buscarPorEmail("joao@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha12345", "hash-da-senha")).thenReturn(true);
        when(jwtUtil.gerarToken(usuarioId, "joao@email.com")).thenReturn("token-jwt");

        LoginResponse response = useCase.executar(request);

        assertEquals("token-jwt", response.token());
        assertEquals("Bearer", response.tipo());
    }

    @Test
    void deveLancarExcecaoQuandoEmailInvalido() {
        LoginRequest request = new LoginRequest("naoexiste@email.com", "senha12345");

        when(usuarioRepository.buscarPorEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.executar(request));
    }

    @Test
    void deveLancarExcecaoQuandoSenhaInvalida() {
        Usuario usuario = new Usuario("João", "joao@email.com", "hash-da-senha");
        LoginRequest request = new LoginRequest("joao@email.com", "senha-errada");

        when(usuarioRepository.buscarPorEmail("joao@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha-errada", "hash-da-senha")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> useCase.executar(request));
    }
}
