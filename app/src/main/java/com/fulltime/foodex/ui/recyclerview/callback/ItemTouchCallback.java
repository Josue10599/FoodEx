package com.fulltime.foodex.ui.recyclerview.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.ui.recyclerview.adapter.ClienteAdapter;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;

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
        if (clienteAdapter == null) {
            int swipeFlagEsquerdaDireita = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int drafFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(drafFlags, swipeFlagEsquerdaDireita);
        }
        int swipeFlagEsquerdaDireita = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int drafFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
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
        if (produtoAdapter != null)
            produtoAdapter.movimentaItem(posicaoInicial, posicaoFinal);
        else
            clienteAdapter.movimentaCliente(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDaNota = viewHolder.getAdapterPosition();
        removeNota(posicaoDaNota);
    }

    private void removeNota(int posicao) {

    }
}
