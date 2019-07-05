package com.fulltime.foodex.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.R;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.ui.fragments.bottomsheet.AdicionarProdutoFragment;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;
import com.fulltime.foodex.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.ArrayList;

import static com.fulltime.foodex.ui.fragments.bottomsheet.Constantes.BOTTOM_SHEET_FRAGMENT_TAG;

public class ProdutosFragment extends Fragment {

    private ProdutoAdapter produtoAdapter;
    private RecyclerView recyclerViewProdutos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View produtosView = inflater.inflate(R.layout.fragment_produtos, container, false);
        configuraAdapter();
        configuraRecyclerView(produtosView);
        return produtosView;
    }

    private void configuraRecyclerView(View produtosView) {
        recyclerViewProdutos = produtosView.findViewById(R.id.fragment_produtos_recyler_view);
        recyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProdutos.setAdapter(produtoAdapter);
    }

    private void configuraAdapter() {
        produtoAdapter = new ProdutoAdapter(getProdutos());
        produtoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(final int posicao, Object itemSelecionado) {
                assert getFragmentManager() != null;
                new AdicionarProdutoFragment((Produto) itemSelecionado, new AdicionarProdutoFragment.SalvaProdutoListener() {
                    @Override
                    public void produtoSalvo(Produto produto) {
                        produtoAdapter.alteraItem(produto, posicao);
                    }
                }).show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }

    private ArrayList<Produto> getProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Pastel de Queijo", "4,00"));
        final Produto produto = new Produto();
        produto.setNome("Pastel");
        produto.setDescricao("Recheio de Queijo e Carne");
        produto.setValor("4,50");
        produtos.add(produto);
        return produtos;
    }
}
