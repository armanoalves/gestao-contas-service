package com.totvs.gestao_contas_service.infrastructure.persistence.fornecedor;

import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FornecedorRepositoryImpl implements FornecedorRepository {
    private final FornecedorJpaRepository fornecedorJpaRepository;

    public FornecedorRepositoryImpl(FornecedorJpaRepository fornecedorJpaRepository) {
        this.fornecedorJpaRepository = fornecedorJpaRepository;
    }

    @Override
    public Fornecedor salvar(Fornecedor fornecedor) {
        return toDomain(fornecedorJpaRepository.save(toEntity(fornecedor)));
    }

    @Override
    public Optional<Fornecedor> buscarPorId(UUID id) {
        return fornecedorJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Fornecedor> buscarPorNome(String nome) {
        return fornecedorJpaRepository.findByNome(nome).map(this::toDomain);
    }

    @Override
    public List<Fornecedor> listarTodos() {
        return fornecedorJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deletar(UUID id) {
        fornecedorJpaRepository.deleteById(id);
    }

    private FornecedorJpaEntity toEntity(Fornecedor fornecedor) {
        FornecedorJpaEntity entity = new FornecedorJpaEntity();
        entity.setId(fornecedor.getId());
        entity.setNome(fornecedor.getNome());
        entity.setCriadoEm(fornecedor.getCriadoEm());
        entity.setAtualizadoEm(fornecedor.getAtualizadoEm());
        return entity;
    }

    private Fornecedor toDomain(FornecedorJpaEntity entity) {
        return new Fornecedor(
                entity.getId(),
                entity.getNome(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm()
        );
    }
}
