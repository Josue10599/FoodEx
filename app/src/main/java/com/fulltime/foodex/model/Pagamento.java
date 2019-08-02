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

import com.fulltime.foodex.formatter.FormataDinheiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Pagamento implements Serializable {

    private final String id;
    private final FormataDinheiro formataDinheiro = new FormataDinheiro();
    private Cliente cliente;
    private BigDecimal valor;
    private Date dataPagamento;

    public Pagamento() {
        this.id = UUID.randomUUID().toString();
    }

    public Pagamento(Cliente cliente, String valor) {
        this.id = UUID.randomUUID().toString();
        this.dataPagamento = Calendar.getInstance().getTime();
        this.cliente = cliente;
        this.valor = formataDinheiro.getBigDecimal(valor);
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getValor() {
        return valor.doubleValue();
    }

    public void setValor(double valor) {
        this.valor = BigDecimal.valueOf(valor);
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String valorDoPagamentoFormatado() {
        return formataDinheiro.formataValor(valor);
    }

    public String dataPagamentoFormatada() {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(dataPagamento);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento)) return false;

        Pagamento pagamento = (Pagamento) o;

        return getId().equals(pagamento.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
