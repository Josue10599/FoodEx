package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.model.Produto;

import java.util.List;

public class ListaProduto {

    private final List<Produto> produtos;

    public ListaProduto(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
