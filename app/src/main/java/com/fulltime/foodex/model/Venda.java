package com.fulltime.foodex.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Venda {
    private Date dataVenda;
    private Cliente cliente;
    private Produto produtoVendido;
    private boolean estadoVenda;

    public boolean getEstadoVenda() {
        return estadoVenda;
    }

    public void setEstadoVenda(boolean estadoVenda) {
        this.estadoVenda = estadoVenda;
    }

    public String getDataVenda() {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(dataVenda);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Produto getProdutoVendido() {
        return produtoVendido;
    }

    public Venda(Date dataVenda, Cliente cliente, Produto produtoVendido) {
        this.dataVenda = dataVenda;
        this.cliente = cliente;
        this.produtoVendido = produtoVendido;
    }
}
