package com.totvs.gestao_contas_service.infrastructure.persistence.usuario;

import com.totvs.gestao_contas_service.domain.entity.Usuario;
import com.totvs.gestao_contas_service.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioRepositoryImpl implements UsuarioRepository {
    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryImpl(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return toDomain(usuarioJpaRepository.save(toEntity(usuario)));
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioJpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deletar(UUID id) {
        usuarioJpaRepository.deleteById(id);
    }

    private UsuarioJpaEntity toEntity(Usuario u) {
        UsuarioJpaEntity e = new UsuarioJpaEntity();
        e.setId(u.getId());
        e.setNome(u.getNome());
        e.setEmail(u.getEmail());
        e.setSenha(u.getSenha());
        e.setCriadoEm(u.getCriadoEm());
        e.setAtualizadoEm(u.getAtualizadoEm());
        return e;
    }

    private Usuario toDomain(UsuarioJpaEntity e) {
        return new Usuario(e.getId(), e.getNome(), e.getEmail(), e.getSenha(),
                e.getCriadoEm(), e.getAtualizadoEm());
    }
}
