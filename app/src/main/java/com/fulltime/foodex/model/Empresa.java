package com.fulltime.foodex.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Empresa implements Serializable {

    public static final String VAZIO = "...";
    private final String id;
    private Date dataCriacaoEmpresa;
    private String nomeEmpresa;
    private String telefoneEmpresa;
    private String emailEmpresa;
    private String enderecoEmpresa;

    public Empresa() {
        this.id = UUID.randomUUID().toString();
        this.dataCriacaoEmpresa = Calendar.getInstance().getTime();
        nomeEmpresa = VAZIO;
        telefoneEmpresa = VAZIO;
        emailEmpresa = VAZIO;
        enderecoEmpresa = VAZIO;
    }

    public String getId() {
        return id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public Date getDataCriacaoEmpresa() {
        return dataCriacaoEmpresa;
    }

    public void setDataCriacaoEmpresa(Date dataCriacaoEmpresa) {
        this.dataCriacaoEmpresa = dataCriacaoEmpresa;
    }

    public String getTelefoneEmpresa() {
        return telefoneEmpresa;
    }

    public void setTelefoneEmpresa(String telefoneEmpresa) {
        this.telefoneEmpresa = telefoneEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getEnderecoEmpresa() {
        return enderecoEmpresa;
    }

    public void setEnderecoEmpresa(String enderecoEmpresa) {
        this.enderecoEmpresa = enderecoEmpresa;
    }

    public String dataCriacaoEmpresa() {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(dataCriacaoEmpresa);
    }

    public String anoCriacaoEmpresa() {
        return DateFormat.getDateInstance(DateFormat.YEAR_FIELD).format(dataCriacaoEmpresa);
    }

    private boolean isNotEmpty(@NonNull String string) {
        return !string.equals(VAZIO) && !string.equals("");
    }
}
