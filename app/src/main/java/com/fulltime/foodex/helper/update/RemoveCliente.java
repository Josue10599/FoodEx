package com.fulltime.foodex.helper.update;

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
