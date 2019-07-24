package com.fulltime.foodex.model;

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

    public String getValorDaCompra() {
        return formataDinheiro.formataValor(valorDaCompra);
    }

    public void setValorDaCompra(String valorDaCompra) {
        this.valorDaCompra = formataDinheiro.getBigDecimal(valorDaCompra);
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
}
