package com.totvs.gestao_contas_service.domain.entity;

import com.totvs.gestao_contas_service.domain.valueobject.Descricao;
import com.totvs.gestao_contas_service.domain.valueobject.Dinheiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private Fornecedor fornecedor;
    private Dinheiro valor;
    private Descricao descricao;

    @BeforeEach
    void setUp() {
        fornecedor = new Fornecedor("Fornecedor Teste");
        valor = new Dinheiro(new BigDecimal("500.00"));
        descricao = new Descricao("Serviço de consultoria");
    }

    @Test
    void deveCriarContaPendente() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        assertNotNull(conta.getId());
        assertEquals(Situacao.PENDENTE, conta.getSituacao());
        assertNull(conta.getDataPagamento());
    }

    @Test
    void deveRejeitarDataVencimentoNoPassado() {
        assertThrows(IllegalArgumentException.class,
                () -> Conta.criar(LocalDate.now().minusDays(1), valor, descricao, fornecedor));
    }

    @Test
    void devePagarContaPendente() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.pagar(LocalDate.now());
        assertEquals(Situacao.PAGO, conta.getSituacao());
        assertEquals(LocalDate.now(), conta.getDataPagamento());
    }

    @Test
    void naoDevePagarContaJaPaga() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.pagar(LocalDate.now());
        assertThrows(IllegalStateException.class, () -> conta.pagar(LocalDate.now()));
    }

    @Test
    void naoDevePagarContaCancelada() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.cancelar();
        assertThrows(IllegalStateException.class, () -> conta.pagar(LocalDate.now()));
    }

    @Test
    void deveCancelarContaPendente() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.cancelar();
        assertEquals(Situacao.CANCELADO, conta.getSituacao());
    }

    @Test
    void naoDeveCancelarContaPaga() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.pagar(LocalDate.now());
        assertThrows(IllegalStateException.class, conta::cancelar);
    }

    @Test
    void naoDeveCancelarContaJaCancelada() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.cancelar();
        assertThrows(IllegalStateException.class, conta::cancelar);
    }

    @Test
    void deveAtualizarContaPendente() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        Dinheiro novoValor = new Dinheiro(new BigDecimal("600.00"));
        Descricao novaDescricao = new Descricao("Nova descrição");
        LocalDate novaData = LocalDate.now().plusDays(60);

        conta.atualizarDados(novaData, novoValor, novaDescricao, fornecedor);

        assertEquals(novaData, conta.getDataVencimento());
        assertEquals(novoValor.valor(), conta.getValor().valor());
        assertEquals(novaDescricao.texto(), conta.getDescricao().texto());
    }

    @Test
    void naoDeveAtualizarContaPaga() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.pagar(LocalDate.now());
        assertThrows(IllegalStateException.class,
                () -> conta.atualizarDados(LocalDate.now().plusDays(60), valor, descricao, fornecedor));
    }

    @Test
    void naoDeveAtualizarContaCancelada() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.cancelar();
        assertThrows(IllegalStateException.class,
                () -> conta.atualizarDados(LocalDate.now().plusDays(60), valor, descricao, fornecedor));
    }

    @Test
    void naoDeveAtualizarComDataVencimentoNoPassado() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        assertThrows(IllegalArgumentException.class,
                () -> conta.atualizarDados(LocalDate.now().minusDays(1), valor, descricao, fornecedor));
    }

    @Test
    void deveIdentificarContaVencida() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        assertFalse(conta.estaVencida());
    }

    @Test
    void deveIdentificarContaPagaEmDia() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        conta.pagar(LocalDate.now().plusDays(25));
        assertTrue(conta.estaPagaEmDia());
    }

    @Test
    void deveCalcularDiasEmAtraso() {
        Conta conta = Conta.criar(LocalDate.now().plusDays(30), valor, descricao, fornecedor);
        LocalDate vencimento = LocalDate.now().plusDays(30);
        conta.pagar(vencimento.plusDays(5));
        assertEquals(5, conta.diasEmAtraso());
    }
}