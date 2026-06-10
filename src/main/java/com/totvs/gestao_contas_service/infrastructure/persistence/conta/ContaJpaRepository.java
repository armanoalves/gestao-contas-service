package com.totvs.gestao_contas_service.infrastructure.persistence.conta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface ContaJpaRepository extends JpaRepository<ContaJpaEntity, UUID> {
    @Query("""
    SELECT c FROM ContaJpaEntity c 
    WHERE (:dataVencimento IS NULL OR c.dataVencimento = :dataVencimento)
    AND (:descricao IS NULL OR LOWER(c.descricao) LIKE LOWER(
    CONCAT('%', :descricao, '%')))
    ORDER BY c.dataVencimento DESC
    """)
    Page<ContaJpaEntity> filtrar(
            @Param("dataVencimento") LocalDate dataVencimento,
            @Param("descricao") String descricao,
            Pageable pageable
            );

    @Query("""
    SELECT COUNT(c) FROM ContaJpaEntity c
    WHERE (:dataVencimento IS NULL OR c.dataVencimento = :dataVencimento)
    AND (:descricao IS NULL OR LOWER(c.descricao) LIKE LOWER(
    CONCAT('%', :descricao, '%')))
    """)
    long contarFiltradas(
            @Param("dataVencimento") LocalDate dataVencimento,
            @Param("descricao") String descricao
    );

    @Query("""
    SELECT COALESCE(SUM(c.valor), 0) FROM ContaJpaEntity c
    WHERE c.situacao = 'PAGO'
    AND c.dataPagamento BETWEEN :inicio AND :fim
    """)
    BigDecimal totalPagoPorPeriodo(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("""
    SELECT COUNT(c) FROM ContaJpaEntity c
    WHERE c.situacao = 'PAGO'
    AND c.dataPagamento BETWEEN :inicio AND :fim
    """)
    long contarPagasPorPeriodo(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );
}
