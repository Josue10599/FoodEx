package com.fulltime.foodex.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class VendaTest {

    private final Cliente cliente = new Cliente(
            "Josue",
            "Lopes",
            "(14) 99802-1667",
            "josue10599@gmail.com",
            "432.418.048-28");
    private final Produto produto = new Produto("Pastel", "10,5");

    @Test
    public void deve_registrarEstadoVendaPago_QuandoClientePagar() {
        new Venda(cliente, true, produto, 1);
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(false)));
    }

    @Test
    public void deve_RegistrarValorDaVenda_QuandoClienteNaoPagar() {
        new Venda(cliente, false, produto, 1);
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(true)));
        Assert.assertThat(cliente.getValorEmDeficit(), is(equalTo("10,50")));
    }

    @Test
    public void deve_RegistrarValorDaVenda_QuandoClienteComprarVariosProdutos() {
        Venda venda = new Venda(cliente, true, produto, 3);
        Assert.assertThat(venda.valorDaCompraFormatado(), is(equalTo("31,50")));
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(false)));
    }

    @Test
    public void deve_RegistrarPagamento_QuandoClientePagarQuantia() {
        new Venda(cliente, true, produto, 1);
        Assert.assertThat(cliente.getValorEmDeficit(), is(equalTo("0,00")));
    }

    @Test
    public void deve_RegistrarPagamentosParcial_QuandoClienteNaoPagarQuantiaCompleta() {
        new Venda(cliente, false, produto, 1);
        cliente.valorPago("0,50");
        Assert.assertThat(cliente.getValorEmDeficit(), is(equalTo("10,00")));
    }

}
