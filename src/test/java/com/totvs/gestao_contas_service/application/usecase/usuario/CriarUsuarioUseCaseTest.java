package com.totvs.gestao_contas_service.application.usecase.usuario;

import com.totvs.gestao_contas_service.application.dto.usuario.CriarUsuarioRequest;
import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.application.mapper.UsuarioMapper;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarUsuarioUseCaseTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioMapper usuarioMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private CriarUsuarioUseCase useCase;

    @Test
    void deveCriarUsuario() {
        CriarUsuarioRequest request = new CriarUsuarioRequest("João", "joao@email.com", "senha12345");

        when(usuarioRepository.buscarPorEmail("joao@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha12345")).thenReturn("hash-da-senha");
        when(usuarioRepository.salvar(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));
        when(usuarioMapper.toResponse(any(Usuario.class))).thenReturn(
                new UsuarioResponse(null, "João", "joao@email.com", null, null)
        );

        UsuarioResponse response = useCase.executar(request);

        assertEquals("João", response.nome());
        assertEquals("joao@email.com", response.email());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).salvar(captor.capture());
        assertEquals("hash-da-senha", captor.getValue().getSenha());
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        CriarUsuarioRequest request = new CriarUsuarioRequest("João", "joao@email.com", "senha12345");

        when(usuarioRepository.buscarPorEmail("joao@email.com")).thenReturn(Optional.of(new Usuario("João", "joao@email.com", "senha12345")));

        assertThrows(IllegalArgumentException.class, () -> useCase.executar(request));
        verify(usuarioRepository, never()).salvar(any());
    }
}
