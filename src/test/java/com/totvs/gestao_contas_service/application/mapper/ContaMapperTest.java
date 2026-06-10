package com.totvs.gestao_contas_service.application.mapper;

import com.totvs.gestao_contas_service.application.dto.conta.ContaResponse;
import com.totvs.gestao_contas_service.domain.entity.Conta;
import com.totvs.gestao_contas_service.domain.entity.Fornecedor;
import com.totvs.gestao_contas_service.domain.entity.Situacao;
import com.totvs.gestao_contas_service.domain.valueobject.Descricao;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContaMapperTest {

    private final ContaMapper mapper = new ContaMapper();

    @Test
    void deveConverterContaParaResponse() {
        Fornecedor fornecedor = new Fornecedor("Fornecedor Teste");
        Conta conta = Conta.criar(
                LocalDate.now().plusDays(30),
                new Dinheiro(new BigDecimal("500.00")),
                new Descricao("Descrição da conta"),
                fornecedor
        );

        ContaResponse response = mapper.toResponse(conta);

        assertEquals(conta.getId(), response.id());
        assertEquals(conta.getDataVencimento(), response.dataVencimento());
        assertEquals(conta.getSituacao(), response.situacao());
        assertEquals(0, conta.getValor().valor().compareTo(response.valor()));
        assertEquals(conta.getDescricao().texto(), response.descricao());
        assertEquals(fornecedor.getId(), response.fornecedorId());
        assertEquals(fornecedor.getNome(), response.fornecedorNome());
    }

    @Test
    void deveConverterBigDecimalParaDinheiro() {
        BigDecimal valor = new BigDecimal("250.00");
        Dinheiro dinheiro = mapper.toDinheiro(valor);
        assertEquals(0, valor.compareTo(dinheiro.valor()));
    }

    @Test
    void deveConverterStringParaDescricao() {
        String texto = "Pagamento de fornecedor";
        Descricao descricao = mapper.toDescricao(texto);
        assertEquals(texto, descricao.texto());
    }

    @Test
    void deveMapearContaPagaCorretamente() {
        Fornecedor fornecedor = new Fornecedor("Fornecedor");
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), new Dinheiro(new BigDecimal("500.00")), new Descricao("Desc"), fornecedor);
        conta.pagar(LocalDate.now());

        ContaResponse response = mapper.toResponse(conta);

        assertEquals(Situacao.PAGO, response.situacao());
        assertEquals(LocalDate.now(), response.dataPagamento());
    }
}
