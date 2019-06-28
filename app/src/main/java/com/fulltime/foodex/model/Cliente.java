package com.fulltime.foodex.model;


import androidx.annotation.NonNull;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Cliente {
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String cpf;
    private BigDecimal valor;

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

    public void setValor(String valor) {
        this.valor = new BigDecimal(valor).setScale(2, HALF_UP);
    }

    public String getValor() {
        return valor.toString();
    }

    @NonNull
    public String getPrimeiraLetraNome() {
        return getNome().substring(0,1).toUpperCase();
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getPrimeiraLetraSobrenome() {
        if(sobrenome != null) return getSobrenome().substring(0,1).toUpperCase();
        return "";
    }
}
