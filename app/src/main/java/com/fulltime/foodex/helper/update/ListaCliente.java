package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.model.Cliente;

import java.util.List;

public class ListaCliente {

    private final List<Cliente> clientes;

    public ListaCliente(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
