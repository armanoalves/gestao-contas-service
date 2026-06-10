package com.totvs.gestao_contas_service.infrastructure.persistence.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FornecedorJpaRepository extends JpaRepository<FornecedorJpaEntity, UUID> {
    Optional<FornecedorJpaEntity> findByNome(String nome);
}
