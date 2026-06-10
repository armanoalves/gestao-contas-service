package com.totvs.gestao_contas_service.application.usecase.usuario;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioMapper usuarioMapper;

    @InjectMocks private BuscarUsuarioUseCase buscarUsuarioUseCase;

    @Test
    void deveBuscarUsuario() {
        var id = UUID.randomUUID();
        var usuario = new Usuario(id, "João", "joao@email.com", "hash",
                LocalDateTime.now(), LocalDateTime.now());
        var response = new UsuarioResponse(id, "João", "joao@email.com",
                usuario.getCriadoEm(), usuario.getAtualizadoEm());

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        var result = buscarUsuarioUseCase.executar(id);

        assertEquals("João", result.nome());
        assertEquals("joao@email.com", result.email());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        var id = UUID.randomUUID();

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> buscarUsuarioUseCase.executar(id));
    }
}
