package com.fulltime.foodex.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class VendaTest {

    Cliente cliente = new Cliente(
            "Josue",
            "Lopes",
            "(14) 99802-1667",
            "josue10599@gmail.com",
            "432.418.048-28");
    Produto produto = new Produto("Pastel", "10,5");

    @Test
    public void deve_registrarEstadoVendaPago_QuandoClientePagar() {
        Venda venda = new Venda(cliente, true, produto);
        Assert.assertThat(venda.vendaPagaCompletamente(), is(equalTo(true)));
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(false)));
    }

    @Test
    public void deve_RegistrarValorDaVenda_QuandoClienteNaoPagar() {
        Venda venda = new Venda(cliente, false, produto);
        Assert.assertThat(venda.getValorEmDeficit(), is(equalTo("10,50")));
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(true)));
        Assert.assertThat(cliente.getValorEmDeficit(), is(equalTo("10,50")));
    }

    @Test
    public void deve_RegistrarValorDaVenda_QuandoClienteComprarVariosProdutos() {
        Venda venda = new Venda(cliente, true, produto, produto, produto);
        Assert.assertThat(venda.getValorDaCompra(), is(equalTo("31,50")));
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(false)));
    }

    @Test
    public void deve_RegistrarPagamento_QuandoClientePagarQuantia() {
        Venda venda = new Venda(cliente, false, produto);
        venda.registrarPagamento("10,50");
        Assert.assertThat(venda.vendaPagaCompletamente(), is(equalTo(true)));
        Assert.assertThat(venda.getValorEmDeficit(), is(equalTo("0,00")));
        Assert.assertThat(cliente.getValorEmDeficit(), is(equalTo("0,00")));
    }

    @Test
    public void deve_RegistrarPagamentosParcial_QuandoClienteNaoPagarQuantiaCompleta() {
        Venda venda = new Venda(cliente, false, produto);
        venda.registrarPagamento("0,50");
        Assert.assertThat(venda.vendaPagaCompletamente(), is(equalTo(false)));
        Assert.assertThat(venda.getValorEmDeficit(), is(equalTo("10,00")));
        Assert.assertThat(cliente.getValorEmDeficit(), is(equalTo("10,00")));
    }

}
