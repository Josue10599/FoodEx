package com.fulltime.foodex.ui.recyclerview.callback;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.helper.update.RemoveCliente;
import com.fulltime.foodex.helper.update.RemoveProduto;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.ui.recyclerview.adapter.ClienteAdapter;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;

import org.greenrobot.eventbus.EventBus;

public class ItemTouchCallback extends ItemTouchHelper.Callback {

    private ProdutoAdapter produtoAdapter;
    private ClienteAdapter clienteAdapter;

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        try {
            produtoAdapter = (ProdutoAdapter) recyclerView.getAdapter();
            clienteAdapter = null;
        } catch (Exception e) {
            produtoAdapter = null;
            clienteAdapter = (ClienteAdapter) recyclerView.getAdapter();
        }
        int swipeFlagEsquerdaDireita = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int drafFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(drafFlags, swipeFlagEsquerdaDireita);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocaPosicaoDasNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocaPosicaoDasNotas(int posicaoInicial, int posicaoFinal) {
        if (isFragmentListaProdutos())
            produtoAdapter.movimentaProduto(posicaoInicial, posicaoFinal);
        else
            clienteAdapter.movimentaCliente(posicaoInicial, posicaoFinal);
    }

    private boolean isFragmentListaProdutos() {
        return produtoAdapter != null;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
        int posicao = viewHolder.getAdapterPosition();
        removeItem(posicao);
    }

    private void removeItem(int posicao) {
        if (isFragmentListaProdutos()) {
            Produto produtoDeletado = produtoAdapter.getProduto(posicao);
            produtoAdapter.removeProduto(posicao);
            produtoAdapter.insereProdutoNaPosicao(produtoDeletado, posicao);
            EventBus.getDefault().post(new RemoveProduto(produtoDeletado, produtoAdapter, posicao));
        } else {
            Cliente clienteDeletado = clienteAdapter.getCliente(posicao);
            clienteAdapter.removeCliente(posicao);
            clienteAdapter.insereCliente(clienteDeletado, posicao);
            EventBus.getDefault().post(new RemoveCliente(clienteDeletado, clienteAdapter, posicao));
        }
    }
}
