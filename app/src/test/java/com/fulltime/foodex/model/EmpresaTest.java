package com.fulltime.foodex.model;

import org.junit.Assert;
import org.junit.Test;

public class EmpresaTest {

    private Empresa empresa = new Empresa();

    @Test
    public void deve_AdicionarUmaEmpresaVazia_QuandoUmaEmpresaEhCriada() {
        Assert.assertEquals("...", empresa.getNomeEmpresa());
        Assert.assertEquals("...", empresa.getEmailEmpresa());
        Assert.assertEquals("...", empresa.getTelefoneEmpresa());
        Assert.assertEquals("...", empresa.getEnderecoEmpresa());
    }

    @Test
    public void deve_AdicionarAsMudancas_QuandoNovasVariaveisSaoAdicionadas() {
        empresa.setTelefoneEmpresa("(14) 99802-1667");
        Assert.assertEquals("(14) 99802-1667", empresa.getTelefoneEmpresa());
    }

    @Test
    public void deve_NaoAdicionarUmEspacoVazio_QuandoEhEnviadoUmValorVazio() {
        empresa.setNomeEmpresa("");
        Assert.assertEquals("...", empresa.getNomeEmpresa());
    }
}

