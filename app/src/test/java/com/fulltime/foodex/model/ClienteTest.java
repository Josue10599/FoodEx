package com.fulltime.foodex.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ClienteTest {

    private final Cliente cliente = new Cliente();

    @Test
    public void deve_DevolverAPrimeiraLetraDoNomeDoCliente_QuandoSeuNomeEhCadastrado() {
        cliente.setNome("Josue");
        Assert.assertThat(cliente.primeiraLetraNome(), is(equalTo("J")));
    }

    @Test
    public void deve_DevolverAPrimeiraLetraDoSobrenomeDoCliente_QuandoSeuSobrenomeEhCadastrado() {
        cliente.setSobrenome("Lopes");
        Assert.assertThat(cliente.primeiraLetraSobrenome(), is(equalTo("L")));
    }

    @Test
    public void deve_DevolverVerdadeiro_QuandoClienteEstiverDevendo() {
        cliente.setValorEmDeficit("10,00");
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(true)));
    }

    @Test
    public void deve_DevolverFalso_QuandoClienteNaoEstiverDevendo() {
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(false)));
    }

    @Test
    public void deve_DevolverQuantiaEmDeficitFormata_QuandoEstiverDevendo() {
        cliente.setValorEmDeficit("10");
        Assert.assertThat(cliente.getValorEmDeficit(), is(equalTo("10,00")));
    }

    @Test
    public void deve_DescontarValor_QuandoClientePagar() {
        cliente.setValorEmDeficit("10");
        cliente.valorPago("10");
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(false)));
    }

    @Test
    public void deve_DescontarValor_QuandoClientePagarUmValorMenorEContinuarDevendo() {
        cliente.setValorEmDeficit("10");
        System.out.println(cliente.getValorEmDeficit());
        cliente.valorPago("9");
        System.out.println(cliente.getValorEmDeficit());
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(true)));
    }

    @Test
    public void deve_DevolverONumeroCadastradoSemFormatacao() {
        cliente.setTelefone("(14) 0000-0000");
        Assert.assertEquals("1400000000", cliente.telefoneSemFormatacao());
    }

}
