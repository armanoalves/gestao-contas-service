package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarUsuariosUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioMapper usuarioMapper;

    @InjectMocks private ListarUsuariosUseCase listarUsuariosUseCase;

    @Test
    void deveListarUsuarios() {
        var usuario = new Usuario(UUID.randomUUID(), "João", "joao@email.com", "hash",
                LocalDateTime.now(), LocalDateTime.now());
        var response = new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getCriadoEm(), usuario.getAtualizadoEm());

        when(usuarioRepository.listarTodos()).thenReturn(List.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        var resultados = listarUsuariosUseCase.executar();

        assertEquals(1, resultados.size());
        assertEquals("João", resultados.getFirst().nome());
    }

    @Test
    void deveRetornarListaVazia() {
        when(usuarioRepository.listarTodos()).thenReturn(List.of());

        var resultados = listarUsuariosUseCase.executar();

        assertTrue(resultados.isEmpty());
    }
}
