package com.totvs.gestao_contas_service.domain.repository;

import com.totvs.gestao_contas_service.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(UUID id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> listarTodos();
    void deletar(UUID id);
}
