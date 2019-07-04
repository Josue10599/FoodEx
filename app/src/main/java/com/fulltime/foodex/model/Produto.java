package com.fulltime.foodex.model;

import com.fulltime.foodex.formatter.FormataDinheiro;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {
    private String nome;
    private BigDecimal valor;
    private FormataDinheiro formataDinheiro = new FormataDinheiro();

    public Produto() {
        this.nome = "";
        this.valor = new BigDecimal("0");
    }

    public Produto(String nome, String valor) {
        setNome(nome);
        setValor(valor);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return formataDinheiro.formataValor(this.valor);
    }

    public void setValor(String valor) {
        this.valor = formataDinheiro.getBigDecimal(valor);
    }

    public boolean produtoPreenchido() {
        return !nome.isEmpty();
    }
}
