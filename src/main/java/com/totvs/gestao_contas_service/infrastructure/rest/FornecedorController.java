package com.totvs.gestao_contas_service.infrastructure.rest;

import com.totvs.gestao_contas_service.application.dto.fornecedor.AtualizarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.CriarFornecedorRequest;
import com.totvs.gestao_contas_service.application.dto.fornecedor.FornecedorResponse;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.AtualizarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.BuscarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.CriarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.DeletarFornecedorUseCase;
import com.totvs.gestao_contas_service.application.usecase.fornecedor.ListarFornecedoresUseCase;
import com.totvs.gestao_contas_service.infrastructure.rest.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fornecedores")
@Tag(name = "Fornecedores", description = "Operações de gerenciamento de fornecedores")
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
    @Operation(summary = "Criar fornecedor", description = "Cadastra um novo fornecedor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<FornecedorResponse> criar(@Valid @RequestBody CriarFornecedorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarFornecedor.executar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fornecedor por ID", description = "Retorna os detalhes de um fornecedor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fornecedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<FornecedorResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(buscarFornecedor.executar(id));
    }

    @GetMapping
    @Operation(summary = "Listar fornecedores", description = "Retorna todos os fornecedores cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<FornecedorResponse>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(listarFornecedores.executar());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fornecedor", description = "Atualiza os dados de um fornecedor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<FornecedorResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AtualizarFornecedorRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(atualizarFornecedor.executar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar fornecedor", description = "Remove um fornecedor do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Fornecedor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarFornecedor.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
