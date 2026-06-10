package com.totvs.gestao_contas_service.infrastructure.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitmqConfig {
    public static final String EXCHANGE = "conta.importacao.exchange";
    public static final String QUEUE = "conta.importacao.queue";
    public static final String ROUTING_KEY = "conta.importacao.routingkey";
    public static final String DLQ = "conta.importacao.dlq";
    public static final String DLQ_ROUTING_KEY = "conta.importacao.dlq.routingkey";

    @Bean
    public DirectExchange importacaoExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue importacaoQueue() {
        return QueueBuilder.durable(QUEUE)
                .deadLetterExchange(EXCHANGE)
                .deadLetterRoutingKey(DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue importacaoDlq() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding importacaoBinding(Queue importacaoQueue, DirectExchange importacaoExchange) {
        return BindingBuilder.bind(importacaoQueue).to(importacaoExchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding importacaoDlqBinding(Queue importacaoDlq, DirectExchange importacaoExchange) {
        return BindingBuilder.bind(importacaoDlq).to(importacaoExchange).with(DLQ_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
