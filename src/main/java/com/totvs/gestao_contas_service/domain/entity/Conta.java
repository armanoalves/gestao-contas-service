package com.totvs.gestao_contas_service.domain.entity;

import com.totvs.gestao_contas_service.domain.valueobject.Descricao;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Conta {
    private final UUID id;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Situacao situacao;
    private Dinheiro valor;
    private Descricao descricao;
    private Fornecedor fornecedor;
    private final LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public Conta(LocalDate dataVencimento, Dinheiro valor, Descricao descricao, Fornecedor fornecedor) {
        this.id = UUID.randomUUID();
        this.dataVencimento = Objects.requireNonNull(dataVencimento, "Data de vencimento é obrigatória.");
        this.valor = Objects.requireNonNull(valor, "O valor da conta é obrigatório.");
        this.descricao = Objects.requireNonNull(descricao, "A descrição da conta é obrigatória.");
        this.fornecedor = Objects.requireNonNull(fornecedor, "O fornecedor é obrigatório.");
        this.dataPagamento = null;
        this.situacao = Situacao.PENDENTE;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    public Conta(UUID id, LocalDate dataVencimento, LocalDate dataPagamento, Situacao situacao,
                 BigDecimal valor, String descricao, Fornecedor fornecedor, LocalDateTime criadoEm,
                 LocalDateTime atualizadoEm) {
        this.id = id;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.situacao = situacao;
        this.valor = new Dinheiro(valor);
        this.descricao = new Descricao(descricao);
        this.fornecedor = fornecedor;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public static Conta criar(LocalDate dataVencimento, Dinheiro valor,
                              Descricao descricao, Fornecedor fornecedor) {
        if (dataVencimento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de vencimento não pode ser no passado");
        }
        return new Conta(dataVencimento, valor, descricao, fornecedor);
    }

    public void atualizarDados(LocalDate dataVencimento, Dinheiro valor, Descricao descricao, Fornecedor fornecedor) {
        if (this.situacao != Situacao.PENDENTE) {
            throw new IllegalStateException("Apenas contas pendentes podem ser atualizadas.");
        }
        Objects.requireNonNull(dataVencimento, "Data de vencimento é obrigatória.");
        Objects.requireNonNull(valor, "O valor da conta é obrigatório.");
        Objects.requireNonNull(descricao, "A descrição da conta é obrigatória.");
        Objects.requireNonNull(fornecedor, "O fornecedor é obrigatório.");
        if (dataVencimento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de vencimento não pode ser no passado");
        }
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
        this.atualizadoEm = LocalDateTime.now();
    }

    public void pagar(LocalDate dataPagamento) {
        if (this.situacao == Situacao.PAGO) {
            throw new IllegalStateException("Essa conta já está paga.");
        }
        if (this.situacao == Situacao.CANCELADO) {
            throw new IllegalStateException("Conta cancelada não pode ser paga.");
        }
        this.situacao = Situacao.PAGO;
        this.dataPagamento = Objects.requireNonNull(dataPagamento, "Data de pagamento é obrigatória.");
        this.atualizadoEm = LocalDateTime.now();
    }

    public void cancelar() {
        if (this.situacao == Situacao.PAGO) {
            throw new IllegalStateException("Conta já paga não pode ser cancelada.");
        }
        if (this.situacao == Situacao.CANCELADO) {
            throw new IllegalStateException("Essa conta já está cancelada.");
        }
        this.situacao = Situacao.CANCELADO;
        this.atualizadoEm = LocalDateTime.now();
    }

    public boolean estaVencida() {
        return situacao == Situacao.PENDENTE && dataVencimento.isBefore(LocalDate.now());
    }

    public boolean estaPagaEmDia() {
        return situacao == Situacao.PAGO && dataPagamento != null
                && (dataPagamento.isBefore(dataVencimento) || dataPagamento.isEqual(dataVencimento));
    }

    public long diasEmAtraso() {
        if (dataPagamento == null || situacao != Situacao.PAGO) return 0;
        return dataVencimento.until(dataPagamento).getDays();
    }

    public UUID getId() { return id; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public Situacao getSituacao() { return situacao; }
    public Dinheiro getValor() { return valor; }
    public Descricao getDescricao() { return descricao; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
}
