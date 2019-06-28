package com.fulltime.foodex.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ClienteTest {

    @Test
    public void deve_DevolverAPrimeiraLetraDoNomeDoCliente_QuandoSeuNomeEhCadastrado() {
        Cliente cliente = new Cliente();
        cliente.setNome("Josue");
        Assert.assertThat(cliente.getPrimeiraLetraNome(), is(equalTo("J")));
    }

    @Test
    public void deve_DevolverAPrimeiraLetraDoSobrenomeDoCliente_QuandoSeuSobrenomeEhCadastrado() {
        Cliente cliente = new Cliente();
        cliente.setSobrenome("Lopes");
        Assert.assertThat(cliente.getPrimeiraLetraSobrenome(), is(equalTo("L")));
    }

}
