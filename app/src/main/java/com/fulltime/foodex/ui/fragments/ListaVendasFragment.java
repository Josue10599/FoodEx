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
import com.fulltime.foodex.ui.recyclerview.adapter.VendasAdapter;

public class ListaVendasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vendasView = inflater.inflate(R.layout.fragment_vendas, container, false);
        configurarRecyclerView(vendasView);
        return vendasView;
    }

    private void configurarRecyclerView(View vendasView) {
        VendasAdapter vendasAdapter = new VendasAdapter();
        RecyclerView listaVendas = vendasView.findViewById(R.id.fragment_vendas_recycler_view);
        listaVendas.setLayoutManager(new LinearLayoutManager(getContext()));
        listaVendas.setAdapter(vendasAdapter);
    }
}
