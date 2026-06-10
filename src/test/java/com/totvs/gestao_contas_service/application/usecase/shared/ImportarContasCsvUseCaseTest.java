package com.totvs.gestao_contas_service.application.usecase.shared;

import com.totvs.gestao_contas_service.application.dto.shared.ImportacaoResponse;
import com.totvs.gestao_contas_service.domain.event.ImportacaoSolicitadaEvent;
import com.totvs.gestao_contas_service.infrastructure.amqp.RabbitmqConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportarContasCsvUseCaseTest {

    @Mock private RabbitTemplate rabbitTemplate;

    @InjectMocks private ImportarContasCsvUseCase importarContasCsvUseCase;

    @Test
    void deveImportarCsv() throws Exception {
        var arquivo = mock(MultipartFile.class);
        var conteudo = "data_vencimento,valor,descricao,fornecedor_nome\n2026-07-15,1500.00,Serviço,Fornecedor".getBytes();

        when(arquivo.getBytes()).thenReturn(conteudo);

        ImportacaoResponse response = importarContasCsvUseCase.executar(arquivo);

        assertNotNull(response.protocoloId());
        assertEquals("Arquivo recebido e enviado para processamento.", response.mensagem());

        var captor = ArgumentCaptor.forClass(ImportacaoSolicitadaEvent.class);
        verify(rabbitTemplate).convertAndSend(
                eq(RabbitmqConfig.EXCHANGE),
                eq(RabbitmqConfig.ROUTING_KEY),
                captor.capture(),
                any(MessagePostProcessor.class)
        );

        assertArrayEquals(conteudo, captor.getValue().conteudoCsv());
    }

    @Test
    void deveLancarExcecaoQuandoErroDeLeitura() throws Exception {
        var arquivo = mock(MultipartFile.class);

        when(arquivo.getBytes()).thenThrow(new IOException("Erro de leitura"));

        assertThrows(RuntimeException.class,
                () -> importarContasCsvUseCase.executar(arquivo));
        verify(rabbitTemplate, never()).convertAndSend(any(String.class), any(String.class), any(Object.class));
    }
}
