package com.fulltime.foodex.ui.recyclerview.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.helper.update.UpdateData;
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
        int posicaoDaNota = viewHolder.getAdapterPosition();
        removeItem(posicaoDaNota);
    }

    private void removeItem(int posicao) {
        if (isFragmentListaProdutos())
            UpdateData.removeProduto(produtoAdapter.getProduto(posicao), posicao);
        else
            UpdateData.removerCliente(clienteAdapter.getCliente(posicao), posicao);
    }
}
