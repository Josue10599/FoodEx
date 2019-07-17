package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.model.Venda;

import java.util.List;

public class ListaVenda {

    private final List<Venda> vendas;

    public ListaVenda(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public List<Venda> getVendas() {
        return vendas;
    }
}
