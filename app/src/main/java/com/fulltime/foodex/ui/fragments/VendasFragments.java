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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.eventbus.ListaVenda;
import com.fulltime.foodex.helper.eventbus.ListaVendaVazia;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.ui.fragments.bottomsheet.DetalhesVendaFragment;
import com.fulltime.foodex.ui.recyclerview.adapter.VendasAdapter;
import com.fulltime.foodex.ui.scroll.ScrollChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class VendasFragments extends Fragment {
    private final VendasAdapter vendasAdapter = new VendasAdapter();
    private SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vendasView = inflater.inflate(R.layout.fragment_lista, container, false);
        configuraSwipe(vendasView);
        configurarRecyclerView(vendasView);
        UpdateData.listaVendas();
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
        UpdateData.listaVendas();
    }

    @Subscribe
    public void onGetListaVenda(ListaVenda listaVenda) {
        List<Venda> vendas = listaVenda.getVendas();
        if (vendas.size() > 0) swipe.setRefreshing(false);
        vendasAdapter.setLista(vendas);
    }

    @Subscribe
    public void voidListaVenda(ListaVendaVazia listaVendaVazia) {
        swipe.setRefreshing(false);
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
        vendasAdapter.setOnItemClickListener((posicao, itemSelecionado) -> new DetalhesVendaFragment((Venda) itemSelecionado)
                .show(Objects.requireNonNull(getFragmentManager()), BOTTOM_SHEET_FRAGMENT_TAG));
        listaVendas.addOnScrollListener(new ScrollChangeListener());
    }
}
