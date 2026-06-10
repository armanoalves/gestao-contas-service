package com.totvs.gestao_contas_service.infrastructure.rest;

import com.totvs.gestao_contas_service.application.dto.fornecedor.AtualizarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.CriarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.AtualizarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.BuscarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.CriarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.DeletarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.ListarFornecedoresUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {
    private final CriarFornecedorUseCase criarFornecedor;
    private final BuscarFornecedorUseCase buscarFornecedor;
    private final ListarFornecedoresUseCase listarFornecedores;
    private final AtualizarFornecedorUseCase atualizarFornecedor;
    private final DeletarFornecedorUseCase deletarFornecedor;

    public FornecedorController(CriarFornecedorUseCase criarFornecedor,
                                BuscarFornecedorUseCase buscarFornecedor,
                                ListarFornecedoresUseCase listarFornecedores,
                                AtualizarFornecedorUseCase atualizarFornecedor,
                                DeletarFornecedorUseCase deletarFornecedor) {
        this.criarFornecedor = criarFornecedor;
        this.buscarFornecedor = buscarFornecedor;
        this.listarFornecedores = listarFornecedores;
        this.atualizarFornecedor = atualizarFornecedor;
        this.deletarFornecedor = deletarFornecedor;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponse> criar(@Valid @RequestBody CriarFornecedorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarFornecedor.executar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(buscarFornecedor.executar(id));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(listarFornecedores.executar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AtualizarFornecedorRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(atualizarFornecedor.executar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarFornecedor.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
