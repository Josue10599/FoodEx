package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;

public class RemoveProduto {
    private final Produto produto;
    private final ProdutoAdapter adapter;
    private final int posicao;

    public RemoveProduto(Produto produto, ProdutoAdapter adapter, int posicao) {
        this.produto = produto;
        this.adapter = adapter;
        this.posicao = posicao;
    }

    public int getPosicao() {
        return posicao;
    }

    public Produto getProduto() {
        return produto;
    }

    public ProdutoAdapter getAdapter() {
        return adapter;
    }
}
