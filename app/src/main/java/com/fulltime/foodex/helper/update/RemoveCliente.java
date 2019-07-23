package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.ui.recyclerview.adapter.ClienteAdapter;

public class RemoveCliente {
    private final Cliente clienteRemovido;
    private final ClienteAdapter adapter;
    private final int posicao;

    public RemoveCliente(Cliente clienteRemovido, ClienteAdapter adapter, int posicao) {
        this.clienteRemovido = clienteRemovido;
        this.adapter = adapter;
        this.posicao = posicao;
    }

    public int getPosicao() {
        return posicao;
    }

    public Cliente getCliente() {
        return clienteRemovido;
    }

    public ClienteAdapter getAdapter() {
        return adapter;
    }
}
