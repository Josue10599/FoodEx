package com.fulltime.foodex.model;

/*
  FoodEx is a sales management application.
  Copyright (C) 2019 Josue Lopes.
  <p>
  FoodEx is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  <p>
  FoodEx is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  <p>
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import androidx.annotation.NonNull;

import com.fulltime.foodex.formatter.FormataDinheiro;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import static java.math.RoundingMode.HALF_UP;

@IgnoreExtraProperties
public class Cliente implements Serializable {
    private final String id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String cpf;
    private BigDecimal valorEmDeficit;
    private final FormataDinheiro formataDinheiro = new FormataDinheiro();

    public Cliente() {
        id = UUID.randomUUID().toString();
        setNome("");
        setSobrenome("");
        setTelefone("");
        setEmail("");
        setCpf("");
        valorEmDeficit = new BigDecimal("0").setScale(2, HALF_UP);
    }

    public Cliente(String nome, String sobrenome, String telefone, String email, String cpf) {
        id = UUID.randomUUID().toString();
        setNome(nome);
        setSobrenome(sobrenome);
        setTelefone(telefone);
        setEmail(email);
        setCpf(cpf);
        valorEmDeficit = new BigDecimal("0").setScale(2, HALF_UP);
    }

    public String getId() {
        return this.id;
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

    public String telefoneSemFormatacao() {
        return telefone.replace("(", "")
                .replace(")", "")
                .replace(" ", "")
                .replace("-", "");
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

    public String nomeCompleto() {
        return nome + " " + sobrenome;
    }

    @NonNull
    public String primeiraLetraNome() {
        return getNome().substring(0, 1).toUpperCase();
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String primeiraLetraSobrenome() {
        if (!sobrenome.isEmpty()) return getSobrenome().substring(0, 1).toUpperCase();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return nomeCompleto();
    }
}
