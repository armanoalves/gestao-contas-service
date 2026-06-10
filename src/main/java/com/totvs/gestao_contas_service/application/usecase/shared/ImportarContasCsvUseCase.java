package com.totvs.gestao_contas_service.application.usecase.shared;

import com.totvs.gestao_contas_service.application.dto.shared.ImportacaoResponse;
import com.totvs.gestao_contas_service.domain.event.ImportacaoSolicitadaEvent;
import com.totvs.gestao_contas_service.infrastructure.amqp.RabbitmqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImportarContasCsvUseCase {
    private final RabbitTemplate rabbitTemplate;

    public ImportarContasCsvUseCase(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ImportacaoResponse executar(MultipartFile arquivo) {
        try {
            UUID protocolo = UUID.randomUUID();
            byte[] contatudo = arquivo.getBytes();
            rabbitTemplate.convertAndSend(
                    RabbitmqConfig.EXCHANGE,
                    RabbitmqConfig.ROUTING_KEY,
                    new ImportacaoSolicitadaEvent(protocolo, contatudo),
                    message -> message
            );

            return new ImportacaoResponse(protocolo,
                    "Arquivo recebido e enviado para processamento.");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo CSV.", e);
        }
    }
}
