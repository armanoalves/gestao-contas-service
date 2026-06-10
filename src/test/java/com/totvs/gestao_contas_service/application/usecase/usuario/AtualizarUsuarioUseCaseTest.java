package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.AtualizarUsuarioRequest;
import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.exception.EntityNotFoundException;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarUsuarioUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioMapper usuarioMapper;

    @InjectMocks private AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    @Test
    void deveAtualizarUsuario() {
        var id = UUID.randomUUID();
        var request = new AtualizarUsuarioRequest("João Atualizado", "joao@email.com");
        var usuario = new Usuario(id, "João", "joao@email.com", "hash",
                LocalDateTime.now(), LocalDateTime.now());

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.salvar(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario))
                .thenReturn(new UsuarioResponse(id, "João Atualizado", "joao@email.com",
                        usuario.getCriadoEm(), LocalDateTime.now()));

        var response = atualizarUsuarioUseCase.executar(id, request);

        assertEquals("João Atualizado", response.nome());
        verify(usuarioRepository).salvar(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        var id = UUID.randomUUID();
        var request = new AtualizarUsuarioRequest("Nome", "email@email.com");

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> atualizarUsuarioUseCase.executar(id, request));
        verify(usuarioRepository, never()).salvar(any());
    }
}
