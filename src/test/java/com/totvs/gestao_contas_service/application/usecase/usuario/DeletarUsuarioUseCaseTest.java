package com.totvs.gestao_contas_service.application.usecase.usuario;

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
class DeletarUsuarioUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;

    @InjectMocks private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @Test
    void deveDeletarUsuario() {
        var id = UUID.randomUUID();

        when(usuarioRepository.buscarPorId(id))
                .thenReturn(Optional.of(new Usuario(id, "João", "joao@email.com", "hash",
                        LocalDateTime.now(), LocalDateTime.now())));

        deletarUsuarioUseCase.executar(id);

        verify(usuarioRepository).deletar(id);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrado() {
        var id = UUID.randomUUID();

        when(usuarioRepository.buscarPorId(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> deletarUsuarioUseCase.executar(id));
        verify(usuarioRepository, never()).deletar(any());
    }
}
