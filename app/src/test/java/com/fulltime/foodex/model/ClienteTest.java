package com.fulltime.foodex.model;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ClienteTest {

    private Cliente cliente = new Cliente();

    @Test
    public void deve_DevolverAPrimeiraLetraDoNomeDoCliente_QuandoSeuNomeEhCadastrado() {
        cliente.setNome("Josue");
        Assert.assertThat(cliente.getPrimeiraLetraNome(), is(equalTo("J")));
    }

    @Test
    public void deve_DevolverAPrimeiraLetraDoSobrenomeDoCliente_QuandoSeuSobrenomeEhCadastrado() {
        cliente.setSobrenome("Lopes");
        Assert.assertThat(cliente.getPrimeiraLetraSobrenome(), is(equalTo("L")));
    }

    @Test
    public void deve_DevolverVerdadeiro_QuandoClienteEstiverDevendo() throws ParseException {
        cliente.setValorEmDefice("10,00");
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(true)));
    }

    @Test
    public void deve_DevolverFalso_QuandoClienteNaoEstiverDevendo() {
        Assert.assertThat(cliente.estaDevendo(), is(equalTo(false)));
    }

    @Test
    public void deve_DevolverQuantiaEmDeficitFormata_QuandoEstiverDevendo() {
        cliente.setValorEmDefice("10");
        Assert.assertThat(cliente.getValorEmDefice(), is(equalTo("10,00")));
    }

}
