package com.fulltime.foodex.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.update.ListaVenda;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.ui.recyclerview.adapter.VendasAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ListaVendasFragment extends Fragment {

    private final VendasAdapter vendasAdapter = new VendasAdapter();
    private SwipeRefreshLayout swipe;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        UpdateData.listaVendas();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vendasView = inflater.inflate(R.layout.fragment_vendas, container, false);
        configuraSwipe(vendasView);
        configurarRecyclerView(vendasView);
        return vendasView;
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
    public void onCreateVenda(Venda venda) {
        swipe.setRefreshing(false);
        vendasAdapter.adicionaVenda(venda);
    }

    @Subscribe
    public void onGetListaVenda(ListaVenda listaVenda) {
        swipe.setRefreshing(false);
        vendasAdapter.setLista(listaVenda.getVendas());
    }

    private void configuraSwipe(View view) {
        swipe = view.findViewById(R.id.swipe_refresh);
        swipe.setRefreshing(true);
        swipe.setOnRefreshListener(UpdateData::listaVendas);
    }

    private void configurarRecyclerView(View vendasView) {
        RecyclerView listaVendas = vendasView.findViewById(R.id.fragment_vendas_recycler_view);
        listaVendas.setLayoutManager(new LinearLayoutManager(getContext()));
        listaVendas.setAdapter(vendasAdapter);
    }
}
