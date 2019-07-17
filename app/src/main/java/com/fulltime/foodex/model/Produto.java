package com.fulltime.foodex.model;

import androidx.annotation.NonNull;

import com.fulltime.foodex.formatter.FormataDinheiro;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@IgnoreExtraProperties
public class Produto implements Serializable {
    private final String id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private final FormataDinheiro formataDinheiro = new FormataDinheiro();

    public Produto() {
        this.id = UUID.randomUUID().toString();
        setNome("");
        setDescricao("");
        setValor("0");
    }

    public Produto(String nome, String valor) {
        this.id = UUID.randomUUID().toString();
        setNome(nome);
        setValor(valor);
    }

    public String getId() {
        return this.id;
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

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produto produto = (Produto) o;

        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return getNome() + " - " + getDescricao();
    }
}
