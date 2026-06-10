package com.totvs.gestao_contas_service.application.mapper;

import com.totvs.gestao_contas_service.application.dto.usuario.UsuarioResponse;
import com.totvs.gestao_contas_service.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCriadoEm(),
                usuario.getAtualizadoEm()
        );
    }
}
