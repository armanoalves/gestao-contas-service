package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.AtualizarSenhaRequest;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarSenhaUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private AtualizarSenhaUseCase atualizarSenhaUseCase;

    @Test
    void deveAtualizarSenha() {
        var id = UUID.randomUUID();
        var request = new AtualizarSenhaRequest("senhaAntiga", "novaSenha123");
        var usuario = new Usuario(id, "João", "joao@email.com", "hashAntigo",
                LocalDateTime.now(), LocalDateTime.now());

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaAntiga", "hashAntigo")).thenReturn(true);
        when(passwordEncoder.encode("novaSenha123")).thenReturn("novoHash");

        atualizarSenhaUseCase.executar(id, request);

        assertEquals("novoHash", usuario.getSenha());
        verify(usuarioRepository).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        var id = UUID.randomUUID();
        var request = new AtualizarSenhaRequest("antiga", "nova12345");

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> atualizarSenhaUseCase.executar(id, request));
        verify(usuarioRepository, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaAtualInvalida() {
        var id = UUID.randomUUID();
        var request = new AtualizarSenhaRequest("senhaErrada", "novaSenha123");
        var usuario = new Usuario(id, "João", "joao@email.com", "hashCorreto",
                LocalDateTime.now(), LocalDateTime.now());

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "hashCorreto")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> atualizarSenhaUseCase.executar(id, request));
        verify(usuarioRepository, never()).salvar(any());
    }
}
