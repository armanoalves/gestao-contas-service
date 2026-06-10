package com.totvs.gestao_contas_service.infrastructure.rest;

import com.totvs.gestao_contas_service.application.dto.conta.*;
import com.totvs.gestao_contas_service.application.dto.shared.ImportacaoResponse;
import com.totvs.gestao_contas_service.application.dto.shared.PaginacaoResponse;
import com.totvs.gestao_contas_service.application.dto.shared.RelatorioResponse;
import com.totvs.gestao_contas_service.application.usecase.conta.*;
import com.totvs.gestao_contas_service.application.usecase.shared.GerarRelatorioUseCase;
import com.totvs.gestao_contas_service.application.usecase.shared.ImportarContasCsvUseCase;
import com.totvs.gestao_contas_service.application.usecase.shared.ListarContasUseCase;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas")
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
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody CriarContaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarConta.executar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponse> buscar(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(buscarConta.executar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponse> atualizar(@PathVariable UUID id,
                                                   @Valid @RequestBody AtualizarContaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(atualizarConta.executar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarConta.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/pagamento")
    public ResponseEntity<ContaResponse> pagar(@PathVariable UUID id,
                                               @Valid @RequestBody PagarContaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(pagarConta.executar(id, request));
    }

    @PostMapping("/{id}/cancelamento")
    public ResponseEntity<ContaResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(cancelarConta.executar(id));
    }

    @GetMapping
    public ResponseEntity<PaginacaoResponse<ContaResponse>> listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanho,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataVencimento,
            @RequestParam(required = false) String descricao) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(listarContas.executar(pagina, tamanho, dataVencimento, descricao));
    }

    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioResponse> relatorio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.status(HttpStatus.OK).body(gerarRelatorio.executar(inicio, fim));
    }

    @PostMapping("/importacao/csv")
    public ResponseEntity<ImportacaoResponse> importacaoCsv(
            @RequestParam("arquivo") MultipartFile arquivo) {
        return ResponseEntity.status(HttpStatus.OK).body(importarContas.executar(arquivo));
    }
}
