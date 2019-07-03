package com.fulltime.foodex.model;


import androidx.annotation.NonNull;

import com.fulltime.foodex.formatter.FormataDinheiro;

import java.io.Serializable;
import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Cliente implements Serializable {
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String cpf;
    private BigDecimal valorEmDeficit;
    private FormataDinheiro formataDinheiro = new FormataDinheiro();

    public Cliente() {
        setNome("");
        setSobrenome("");
        setTelefone("");
        setEmail("");
        setCpf("");
        valorEmDeficit = new BigDecimal("0").setScale(2, HALF_UP);
    }

    public Cliente(String nome, String sobrenome, String telefone, String email, String cpf) {
        setNome(nome);
        setSobrenome(sobrenome);
        setTelefone(telefone);
        setEmail(email);
        setCpf(cpf);
        valorEmDeficit = new BigDecimal("0").setScale(2, HALF_UP);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getValorEmDeficit() {
        return formataDinheiro.formataValor(valorEmDeficit);
    }

    public void setValorEmDeficit(String valorEmDeficit) {
        this.valorEmDeficit = this.valorEmDeficit.add(formataDinheiro.getBigDecimal(valorEmDeficit));
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    @NonNull
    public String getPrimeiraLetraNome() {
        return getNome().substring(0, 1).toUpperCase();
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getPrimeiraLetraSobrenome() {
        if (sobrenome != null) return getSobrenome().substring(0, 1).toUpperCase();
        return "";
    }

    public void valorPago(String valorPago) {
        valorEmDeficit = valorEmDeficit.subtract(formataDinheiro.getBigDecimal(valorPago));
    }

    public boolean estaDevendo() {
        return valorEmDeficit.compareTo(new BigDecimal("0")) > 0;
    }

    public boolean clienteEhCadastrado() {
        return !getNome().equals("");
    }
}
