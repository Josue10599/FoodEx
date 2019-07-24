package com.fulltime.foodex.ui.fragments;

import android.content.Context;
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
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.ui.fragments.bottomsheet.ImplementaProdutoFragment;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;
import com.fulltime.foodex.ui.recyclerview.callback.ItemTouchCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class ListaProdutosFragment extends Fragment {

    private ProdutoAdapter produtoAdapter = new ProdutoAdapter();
    private SwipeRefreshLayout swipe;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        UpdateData.listaProdutos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View produtosView = inflater.inflate(R.layout.fragment_produtos, container, false);
        configuraSwipe(produtosView);
        configuraRecyclerView(produtosView);
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
        swipe.setRefreshing(false);
        produtoAdapter.setListaProduto(listaProduto.getProdutos());
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
