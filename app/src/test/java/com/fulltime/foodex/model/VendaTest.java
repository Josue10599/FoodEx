package com.fulltime.foodex.model;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;

public class VendaTest {

    @Test
    public void deve_DevolverDataFormata_QuandoRecebeNovaLocalidade() {
        Cliente cliente = new Cliente();
        cliente.setNome("Josue");
        Produto produto = new Produto();
        produto.setNome("Pastel");
        Venda venda = new Venda(Calendar.getInstance().getTime(), cliente, produto);
        Locale.setDefault(new Locale("pt-br", "BR"));
        Assert.assertThat(venda.getDataVenda(), is(equalTo("28/06/2019")));
    }

}
