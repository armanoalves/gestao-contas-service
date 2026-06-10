package com.totvs.gestao_contas_service.infrastructure.amqp;

import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.event.ImportacaoSolicitadaEvent;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
import com.totvs.gestao_contas_service.domain.repository.FornecedorRepository;
import com.totvs.gestao_contas_service.domain.valueobject.Descricao;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ImportacaoCsvConsumer {
    private static final Logger log = LoggerFactory.getLogger(ImportacaoCsvConsumer.class);
    private final ContaRepository contaRepository;
    private final FornecedorRepository fornecedorRepository;

    public ImportacaoCsvConsumer(ContaRepository contaRepository, FornecedorRepository fornecedorRepository) {
        this.contaRepository = contaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @RabbitListener(queues = RabbitmqConfig.QUEUE)
    public void processar(ImportacaoSolicitadaEvent evento) {
        int linhaAtual = 0;
        int errosValidacao = 0;
        int contasSalvas = 0;
        int errosPersistencia = 0;

        try (var reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(evento.conteudoCsv())))) {
            String linha;
            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                linhaAtual++;
                try {
                    String[] campos = linha.split(",");
                    if (campos.length < 4) {
                        log.warn("Linha {} ignorada: número insuficiente de campos", linhaAtual);
                        errosValidacao++;
                        continue;
                    }

                    LocalDate dataVencimento = LocalDate.parse(campos[0].trim());
                    BigDecimal valor = new BigDecimal(campos[1].trim());
                    String descricao = campos[2].trim();
                    String fornecedorNome = campos[3].trim();

                    Fornecedor fornecedor = fornecedorRepository.buscarPorNome(fornecedorNome)
                            .orElseGet(() -> fornecedorRepository.salvar(new Fornecedor(fornecedorNome)));
                    Conta conta = Conta.criar(
                            dataVencimento,
                            new Dinheiro(valor),
                            new Descricao(descricao),
                            fornecedor
                    );

                    try {
                        contaRepository.salvar(conta);
                        contasSalvas++;
                    } catch (Exception e) {
                        log.warn("Erro ao persistir conta da linha {}: {}", linhaAtual, e.getMessage());
                        errosPersistencia++;
                    }
                } catch (Exception e) {
                    log.warn("Erro ao processar linha {}: {}", linhaAtual, e.getMessage());
                    errosValidacao++;
                }
            }

            log.info("CSV processado: {} contas salvas, {} erros de validação, {} erros de persistência em {} linhas",
                    contasSalvas, errosValidacao, errosPersistencia, linhaAtual);

        } catch (Exception e) {
            log.error("Erro fatal ao processar CSV: {}", e.getMessage(), e);
            throw new AmqpRejectAndDontRequeueException("Erro ao processar CSV", e);
        }
    }
}
