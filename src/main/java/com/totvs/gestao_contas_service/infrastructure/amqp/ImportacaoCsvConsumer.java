package com.totvs.gestao_contas_service.infrastructure.amqp;

import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.event.ImportacaoSolicitadaEvent;
import com.totvs.gestao_contas_service.domain.repository.ContaRepository;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class ImportacaoCsvConsumer {
    private static final Logger log = LoggerFactory.getLogger(ImportacaoCsvConsumer.class);
    private final ContaRepository contaRepository;

    public ImportacaoCsvConsumer(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @RabbitListener(queues = RabbitmqConfig.QUEUE)
    public void processar(ImportacaoSolicitadaEvent evento) {
        List<Conta> contas = new ArrayList<>();
        int linhaAtual = 0;
        int erros = 0;

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
                        erros++;
                        continue;
                    }

                    LocalDate dataVencimento = LocalDate.parse(campos[0].trim());
                    BigDecimal valor = new BigDecimal(campos[1].trim());
                    String descricao = campos[2].trim();
                    String fornecedorNome = campos[3].trim();

                    Fornecedor fornecedor = new Fornecedor(fornecedorNome);
                    Conta conta = Conta.criar(
                            dataVencimento,
                            new Dinheiro(valor),
                            new Descricao(descricao),
                            fornecedor
                    );
                    contas.add(conta);
                } catch (Exception e) {
                    log.warn("Erro ao processar linha {}: {}", linhaAtual, e.getMessage());
                    erros++;
                }
            }

            if (!contas.isEmpty()) {
                contaRepository.salvarTodos(contas);
            }
            log.info("CSV processado: {} contas importadas, {} erros em {} linhas",
                    contas.size(), erros, linhaAtual);

        } catch (Exception e) {
            log.error("Erro fatal ao processar CSV: {}", e.getMessage(), e);
            throw new AmqpRejectAndDontRequeueException("Erro ao processar CSV", e);
        }
    }
}
