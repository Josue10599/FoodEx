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
    private FormataDinheiro formataDinheiro = new FormataDinheiro();
    private Date dataVenda;
    private Cliente cliente;
    private Produto[] produtosVendidos;
    private BigDecimal valorEmDeficit;
    private BigDecimal valorDaCompra;
    private boolean estadoVenda;

    public Venda() {
        this.id = UUID.randomUUID().toString();
        this.dataVenda = Calendar.getInstance().getTime();
        this.valorEmDeficit = new BigDecimal("0");
        this.valorDaCompra = new BigDecimal("0");
        this.estadoVenda = false;
    }

    public Venda(Cliente cliente, boolean pago, Produto... produtoVendido) {
        this.id = UUID.randomUUID().toString();
        this.dataVenda = Calendar.getInstance().getTime();
        this.cliente = cliente;
        this.produtosVendidos = produtoVendido;
        this.estadoVenda = pago;
        this.valorDaCompra = new BigDecimal("0");
        setValorDaCompra();
        this.valorEmDeficit = new BigDecimal("0");
        if (!pago) {
            valorEmDeficit = valorDaCompra;
            cliente.setValorEmDeficit(formataDinheiro.formataValor(valorEmDeficit));
        }
    }

    private void setValorDaCompra() {
        for (Produto produto : produtosVendidos) {
            valorDaCompra = valorDaCompra.add(formataDinheiro.getBigDecimal(produto.getValor()));
        }
    }

    public boolean vendaPagaCompletamente() {
        return estadoVenda;
    }

    private void setEstadoVenda(boolean estadoVenda) {
        this.estadoVenda = estadoVenda;
    }

    public void registrarPagamento(String valor) {
        cliente.valorPago(valor);
        valorEmDeficit = valorEmDeficit.subtract(formataDinheiro.getBigDecimal(valor));
        vendaPaga();
    }

    public String getDataVenda() {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(dataVenda);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Produto getProdutosVendidos(int posicao) {
        return produtosVendidos[posicao];
    }

    public int getNumeroProdutosVendidos() {
        return produtosVendidos.length;
    }

    public String getValorDaCompra() {
        return formataDinheiro.formataValor(valorDaCompra);
    }

    public String getValorEmDeficit() {
        return formataDinheiro.formataValor(valorEmDeficit);
    }

    private void vendaPaga() {
        estadoVenda = valorEmDeficit.compareTo(new BigDecimal("0")) == 0;
    }

    public String getId() {
        return this.id;
    }
}
