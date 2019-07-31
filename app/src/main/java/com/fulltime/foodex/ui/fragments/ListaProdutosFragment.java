package com.fulltime.foodex.ui.fragments;

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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.update.ListaProduto;
import com.fulltime.foodex.helper.update.ListaProdutoVazia;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.ui.fragments.bottomsheet.ImplementaProdutoFragment;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;
import com.fulltime.foodex.ui.recyclerview.callback.ItemTouchCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class ListaProdutosFragment extends Fragment {

    private ProdutoAdapter produtoAdapter = new ProdutoAdapter();
    private SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View produtosView = inflater.inflate(R.layout.fragment_produtos, container, false);
        configuraSwipe(produtosView);
        configuraRecyclerView(produtosView);
        UpdateData.listaProdutos();
        return produtosView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onCreateProduto(Produto produto) {
        swipe.setRefreshing(false);
        produtoAdapter.insereProduto(produto);
    }

    @Subscribe
    public void onGetListaProduto(ListaProduto listaProduto) {
        List<Produto> produtos = listaProduto.getProdutos();
        if (produtos.size() > 0) swipe.setRefreshing(false);
        produtoAdapter.setListaProduto(produtos);
    }

    @Subscribe
    public void voidListaProduto(ListaProdutoVazia listaProdutoVazia) {
        swipe.setRefreshing(false);
    }

    private void configuraSwipe(View clientView) {
        swipe = clientView.findViewById(R.id.swipe_refresh);
        swipe.setRefreshing(true);
        swipe.setOnRefreshListener(UpdateData::listaProdutos);
    }

    private void configuraRecyclerView(View produtosView) {
        configuraProdutoAdapter();
        RecyclerView recyclerViewProdutos = produtosView.findViewById(R.id.fragment_produtos_recyler_view);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProdutos.setAdapter(produtoAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchCallback());
        itemTouchHelper.attachToRecyclerView(recyclerViewProdutos);
    }

    private void configuraProdutoAdapter() {
        produtoAdapter = new ProdutoAdapter();
        produtoAdapter.setOnItemClickListener((posicao, produtoSelecionado) -> {
            assert getFragmentManager() != null;
            new ImplementaProdutoFragment((Produto) produtoSelecionado)
                    .show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
        });
    }
}
