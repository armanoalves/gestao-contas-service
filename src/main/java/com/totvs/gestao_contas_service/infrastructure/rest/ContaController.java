package com.totvs.gestao_contas_service.infrastructure.rest;

import com.totvs.gestao_contas_service.application.dto.conta.*;
import com.totvs.gestao_contas_service.application.dto.shared.ImportacaoResponse;
import com.totvs.gestao_contas_service.application.dto.shared.PaginacaoResponse;
import com.totvs.gestao_contas_service.application.dto.shared.RelatorioResponse;
import com.totvs.gestao_contas_service.infrastructure.rest.handler.ErrorResponse;
import com.totvs.gestao_contas_service.application.usecase.conta.*;
import com.totvs.gestao_contas_service.application.usecase.shared.GerarRelatorioUseCase;
import com.totvs.gestao_contas_service.application.usecase.shared.ImportarContasCsvUseCase;
import com.totvs.gestao_contas_service.application.usecase.shared.ListarContasUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas")
@Tag(name = "Contas", description = "Operações de gestão de contas a pagar")
public class ContaController {
    private final CriarContaUseCase criarConta;
    private final BuscarContaUseCase buscarConta;
    private final PagarContaUseCase pagarConta;
    private final CancelarContaUseCase cancelarConta;
    private final ListarContasUseCase listarContas;
    private final AtualizarContaUseCase atualizarConta;
    private final DeletarContaUseCase deletarConta;
    private final GerarRelatorioUseCase gerarRelatorio;
    private final ImportarContasCsvUseCase importarContas;

    public ContaController(CriarContaUseCase criarConta, BuscarContaUseCase buscarConta,
                           PagarContaUseCase pagarConta, CancelarContaUseCase cancelarConta,
                           ListarContasUseCase listarContas, AtualizarContaUseCase atualizarConta,
                           DeletarContaUseCase deletarConta, GerarRelatorioUseCase gerarRelatorio,
                           ImportarContasCsvUseCase importarContas) {
        this.criarConta = criarConta;
        this.buscarConta = buscarConta;
        this.pagarConta = pagarConta;
        this.cancelarConta = cancelarConta;
        this.listarContas = listarContas;
        this.atualizarConta = atualizarConta;
        this.deletarConta = deletarConta;
        this.gerarRelatorio = gerarRelatorio;
        this.importarContas = importarContas;
    }

    @PostMapping
    @Operation(summary = "Criar conta", description = "Cria uma nova conta a pagar")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody CriarContaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarConta.executar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID", description = "Retorna os detalhes de uma conta específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta encontrada"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ContaResponse> buscar(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(buscarConta.executar(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta", description = "Atualiza os dados de uma conta pendente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conta não está pendente",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ContaResponse> atualizar(@PathVariable UUID id,
                                                    @Valid @RequestBody AtualizarContaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(atualizarConta.executar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar conta", description = "Remove uma conta do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Conta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarConta.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/pagamento")
    @Operation(summary = "Pagar conta", description = "Registra o pagamento de uma conta pendente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conta já está paga ou cancelada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ContaResponse> pagar(@PathVariable UUID id,
                                                @Valid @RequestBody PagarContaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(pagarConta.executar(id, request));
    }

    @PostMapping("/{id}/cancelamento")
    @Operation(summary = "Cancelar conta", description = "Cancela uma conta pendente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conta já está paga ou cancelada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ContaResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(cancelarConta.executar(id));
    }

    @GetMapping
    @Operation(summary = "Listar contas", description = "Lista contas com paginação e filtros opcionais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de contas retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PaginacaoResponse<ContaResponse>> listar(
            @Parameter(description = "Número da página (começa em 0)")
            @RequestParam(defaultValue = "0") int pagina,
            @Parameter(description = "Quantidade de itens por página")
            @RequestParam(defaultValue = "20") int tamanho,
            @Parameter(description = "Filtrar por data de vencimento")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataVencimento,
            @Parameter(description = "Filtrar por descrição (busca parcial)")
            @RequestParam(required = false) String descricao) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(listarContas.executar(pagina, tamanho, dataVencimento, descricao));
    }

    @GetMapping("/relatorio")
    @Operation(summary = "Gerar relatório", description = "Gera relatório de contas pagas em um período")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<RelatorioResponse> relatorio(
            @Parameter(description = "Data de início do período (ISO: yyyy-MM-dd)", required = true,
                    example = "2026-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Data de fim do período (ISO: yyyy-MM-dd)", required = true,
                    example = "2026-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.status(HttpStatus.OK).body(gerarRelatorio.executar(inicio, fim));
    }

    @PostMapping(value = "/importacao/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Importar contas via CSV",
            description = "Importa contas em lote a partir de um arquivo CSV. " +
                          "O processamento é assíncrono via RabbitMQ.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Importação iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Arquivo inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ImportacaoResponse> importacaoCsv(
            @Parameter(description = "Arquivo CSV com as contas a importar", required = true)
            @RequestParam("arquivo") MultipartFile arquivo) {
        return ResponseEntity.status(HttpStatus.OK).body(importarContas.executar(arquivo));
    }
}
