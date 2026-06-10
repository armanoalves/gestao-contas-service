package com.totvs.gestao_contas_service.infrastructure.persistence.conta;

import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.entity.Situacao;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ContaRepositoryImpl implements ContaRepository {
    private final ContaJpaRepository contaJpaRepository;

    public ContaRepositoryImpl(ContaJpaRepository contaJpaRepository) {
        this.contaJpaRepository = contaJpaRepository;
    }

    @Override
    public Conta salvar(Conta conta) {
        return toDomain(contaJpaRepository.save(toEntity(conta)));
    }

    @Override
    public Optional<Conta> buscarPorId(UUID id) {
        return contaJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Conta> listarPaginada(int pagina, int tamanho,
                                      LocalDate dataVencimento, String descricao) {
        return contaJpaRepository.filtrar(
                dataVencimento,
                descricao,
                PageRequest.of(pagina, tamanho)).stream().map(this::toDomain).toList();
    }

    @Override
    public long contar(LocalDate dataVencimento, String descricao) {
        return contaJpaRepository.contarFiltradas(dataVencimento, descricao);
    }

    @Override
    public BigDecimal calcularTotalPagoPorPeriodo(LocalDate inicio, LocalDate fim) {
        return contaJpaRepository.totalPagoPorPeriodo(inicio, fim);
    }

    @Override
    public long contarPagasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return contaJpaRepository.contarPagasPorPeriodo(inicio, fim);
    }

    @Override
    public void salvarTodos(List<Conta> contas) {
        contaJpaRepository.saveAll(contas.stream().map(this::toEntity).toList());
    }

    @Override
    public void deletar(UUID id) {
        contaJpaRepository.deleteById(id);
    }

    private ContaJpaEntity toEntity(Conta conta) {
        ContaJpaEntity e = new ContaJpaEntity();
        e.setId(conta.getId());
        e.setDataVencimento(conta.getDataVencimento());
        e.setDataPagamento(conta.getDataPagamento());
        e.setSituacao(conta.getSituacao().name());
        e.setValor(conta.getValor().valor());
        e.setDescricao(conta.getDescricao().texto());
        e.setFornecedorId(conta.getFornecedor().getId());
        e.setFornecedorNome(conta.getFornecedor().getNome());
        e.setCriadoEm(conta.getCriadoEm());
        e.setAtualizadoEm(conta.getAtualizadoEm());
        return e;
    }

    private Conta toDomain(ContaJpaEntity entity) {
        Fornecedor fornecedor = new Fornecedor(
                entity.getFornecedorId(), entity.getFornecedorNome(),
                entity.getCriadoEm(), entity.getAtualizadoEm()
        );
        return new Conta(
                entity.getId(), entity.getDataVencimento(), entity.getDataPagamento(),
                Situacao.valueOf(entity.getSituacao()), entity.getValor(),
                entity.getDescricao(), fornecedor, entity.getCriadoEm(), entity.getAtualizadoEm()
        );
    }
}
