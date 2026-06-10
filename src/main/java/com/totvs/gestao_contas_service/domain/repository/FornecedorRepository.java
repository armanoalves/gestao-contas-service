package com.totvs.gestao_contas_service.domain.repository;

import com.totvs.gestao_contas_service.domain.entity.Fornecedor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FornecedorRepository {
    Fornecedor salvar(Fornecedor fornecedor);
    Optional<Fornecedor> buscarPorId(UUID id);
    Optional<Fornecedor> buscarPorNome(String nome);
    List<Fornecedor> listarTodos();
    void deletar(UUID id);
}
