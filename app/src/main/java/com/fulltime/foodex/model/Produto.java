package com.fulltime.foodex.model;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Produto {
    private String nome;
    private BigDecimal valor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor.toString();
    }

    public void setValor(String valor) {
        this.valor = new BigDecimal(valor).setScale(2, HALF_UP);
    }
}
