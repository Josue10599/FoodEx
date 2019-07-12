package com.fulltime.foodex.model;

import com.fulltime.foodex.formatter.FormataDinheiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Venda implements Serializable {
    private final String id;
    private FormataDinheiro formataDinheiro = new FormataDinheiro();
    private Date dataVenda;
    private Cliente cliente;
    private List<Produto> produtosVendidos;
    private BigDecimal valorDaCompra;

    public Venda() {
        this.id = UUID.randomUUID().toString();
        this.dataVenda = Calendar.getInstance().getTime();
        this.valorDaCompra = new BigDecimal("0");
    }

    public Venda(Cliente cliente, boolean pago, Produto... produtoVendido) {
        this.id = UUID.randomUUID().toString();
        this.dataVenda = Calendar.getInstance().getTime();
        this.cliente = cliente;
        this.produtosVendidos.addAll(Arrays.asList(produtoVendido));
        this.valorDaCompra = new BigDecimal("0");
        setValorDaCompra();
        if (!pago) {
            cliente.setValorEmDeficit(formataDinheiro.formataValor(valorDaCompra));
        }
    }

    public Venda(Cliente cliente, boolean pago, List<Produto> produtoVendido) {
        this.id = UUID.randomUUID().toString();
        this.dataVenda = Calendar.getInstance().getTime();
        this.cliente = cliente;
        this.produtosVendidos = produtoVendido;
        this.valorDaCompra = new BigDecimal("0");
        setValorDaCompra();
        if (!pago) {
            cliente.setValorEmDeficit(formataDinheiro.formataValor(valorDaCompra));
        }
    }

    private void setValorDaCompra() {
        for (Produto produto : produtosVendidos) {
            valorDaCompra = valorDaCompra.add(formataDinheiro.getBigDecimal(produto.getValor()));
        }
    }

    public String getDataVenda() {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(dataVenda);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Produto getProdutosVendidos(int posicao) {
        return produtosVendidos.get(posicao);
    }

    public int getNumeroProdutosVendidos() {
        return produtosVendidos.size();
    }

    public String getValorDaCompra() {
        return formataDinheiro.formataValor(valorDaCompra);
    }

    public String getId() {
        return this.id;
    }
}
