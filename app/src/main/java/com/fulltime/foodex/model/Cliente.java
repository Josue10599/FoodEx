package com.fulltime.foodex.model;


import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import static java.math.RoundingMode.HALF_UP;

public class Cliente {
    private final DecimalFormat decimalFormat;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String cpf;
    private BigDecimal valorEmDefice;

    public Cliente() {
        decimalFormat = new DecimalFormat("#,##0.00");
        decimalFormat.setParseBigDecimal(true);
        setValorEmDefice("0");
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

    public String getValorEmDefice() {
        return decimalFormat.format(valorEmDefice);
    }

    public void setValorEmDefice(String valorEmDefice) {
        this.valorEmDefice = (getBigDecimal(valorEmDefice)).setScale(2, HALF_UP);
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

    public void quantiaValorPago(String valorPago) {
        valorEmDefice = valorEmDefice.subtract(getBigDecimal(valorPago));
    }

    public boolean estaDevendo() {
        return valorEmDefice.compareTo(new BigDecimal("0")) > 0;
    }

    private BigDecimal getBigDecimal(String valor) {
        try {
            return (BigDecimal) decimalFormat.parse(valor);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new BigDecimal(valor.replace(',', '.'));
    }
}
