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

public class Venda implements Serializable {
    private final String id;
    private final FormataDinheiro formataDinheiro = new FormataDinheiro();
    private Date dataVenda;
    private Cliente cliente;
    private Produto produtoVendido;
    private BigDecimal valorDaCompra;
    private int quantidade;
    private boolean pago;

    public Venda() {
        this.id = UUID.randomUUID().toString();
        this.dataVenda = Calendar.getInstance().getTime();
        this.produtoVendido = new Produto();
        this.cliente = new Cliente();
        this.valorDaCompra = new BigDecimal("0");
    }

    public Venda(Cliente cliente, boolean pago, Produto produtoVendido, int quantidade) {
        this.id = UUID.randomUUID().toString();
        this.dataVenda = Calendar.getInstance().getTime();
        this.cliente = cliente;
        this.produtoVendido = produtoVendido;
        this.valorDaCompra = new BigDecimal("0");
        this.quantidade = quantidade;
        calculaValorDaCompra(quantidade);
        this.pago = pago;
        if (!pago) {
            cliente.setValorEmDeficit(formataDinheiro.formataValor(valorDaCompra));
        }
    }

    private void calculaValorDaCompra(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            valorDaCompra = valorDaCompra.add(formataDinheiro.getBigDecimal(produtoVendido.getValor()));
        }
    }

    public String dataVendaFormatada() {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(dataVenda);
    }

    public Date getDataVenda() {
        return this.dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProdutoVendido() {
        return produtoVendido;
    }

    public void setProdutoVendido(Produto produtoVendido) {
        this.produtoVendido = produtoVendido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String valorDaCompraFormatado() {
        return formataDinheiro.formataValor(valorDaCompra);
    }

    public double getValorDaCompra() {
        return this.valorDaCompra.doubleValue();
    }

    public void setValorDaCompra(double valorDaCompra) {
        this.valorDaCompra = BigDecimal.valueOf(valorDaCompra);
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda)) return false;

        Venda venda = (Venda) o;

        return getId() != null ? getId().equals(venda.getId()) : venda.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }
}
