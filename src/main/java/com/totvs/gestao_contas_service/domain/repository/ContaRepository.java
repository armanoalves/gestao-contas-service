package com.totvs.gestao_contas_service.domain.repository;

import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContaRepository {
    Conta salvar(Conta conta);
    Optional<Conta> buscarPorId(UUID id);
    List<Conta> listarPaginada(int pagina, int tamanho, LocalDate dataVencimento, String descricao);
    long contar(LocalDate dataVencimento, String descricao);
    Dinheiro calcularTotalPagoPorPeriodo(LocalDate inicio, LocalDate fim);
    long contarPagasPorPeriodo(LocalDate inicio, LocalDate fim);
    void salvarTodos(List<Conta> contas);
    void deletar(UUID id);
}
