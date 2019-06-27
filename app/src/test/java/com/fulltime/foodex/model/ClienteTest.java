package com.fulltime.foodex.model;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

import static org.hamcrest.CoreMatchers.*;

public class ClienteTest {

    @Test
    public void deve_DevolverAPrimeiraLetraDoNomeDoCliente_QuandoSeuNomeEhCadastrado() {
        Cliente cliente = new Cliente();
        cliente.setNome("Josue");
        Assert.assertThat(cliente.getPrimeiraLetra(), is(equalTo("J")));
    }

}
