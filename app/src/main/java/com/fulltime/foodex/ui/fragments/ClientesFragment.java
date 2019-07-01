package com.fulltime.foodex.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.R;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.recyclerview.adapter.ClientesAdapter;
import com.fulltime.foodex.recyclerview.adapter.listener.OnItemClickListener;
import com.fulltime.foodex.ui.fragments.bottomsheet.AdicionarClienteFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientesFragment extends Fragment {

    private ClientesAdapter adapter;
    private RecyclerView listaCliente;
    private List<Cliente> dividendos = new ArrayList<>();
    private List<Cliente> todosClientes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View clientView = inflater.inflate(R.layout.fragment_cliente, container, false);
        listaDividendo(getCliente());
        configuraTabMenu(clientView);
        configuraAdapter();
        configuraRecyclerView(clientView);
        configuraFab(clientView);
        return clientView;
    }

    private void configuraFab(View clientView) {
        clientView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowBottomSheetClicked();
            }
        });
    }

    private void listaDividendo(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            if (cliente.estaDevendo()) dividendos.add(cliente);
        }
    }

    private void configuraTabMenu(View clientView) {
        TabLayout tabLayoutMenuCliente = clientView.findViewById(R.id.fragment_cliente_tab_menu);
        tabLayoutMenuCliente.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals(getContext().getString(R.string.fragment_cliente_devedores))) {
                    adapter.alteraLista(dividendos);
                } else {
                    adapter.alteraLista(todosClientes);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void configuraAdapter() {
        todosClientes = getCliente();
        adapter = new ClientesAdapter(todosClientes);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int posicao, Cliente clienteSelecionado) {

            }
        });
    }

    private void configuraRecyclerView(View clientView) {
        listaCliente = clientView.findViewById(R.id.fragment_cliente_recycler_view);
        listaCliente.setItemAnimator(new DefaultItemAnimator());
        listaCliente.setAdapter(adapter);
    }

    private List<Cliente> getCliente() {
        Cliente clienteJosue = new Cliente();
        clienteJosue.setNome("Josue");
        clienteJosue.setSobrenome("Lopes");
        clienteJosue.setTelefone("(14) 99802-1667");
        clienteJosue.setValorEmDefice("0");
        Cliente clienteLopes = new Cliente();
        clienteLopes.setNome("Lopes");
        clienteLopes.setSobrenome("Anchieta");
        clienteLopes.setTelefone("(14) 99802-1667");
        clienteLopes.setValorEmDefice("10");
        return Arrays.asList(clienteJosue, clienteLopes);
    }

    private void onShowBottomSheetClicked() {
        new AdicionarClienteFragment().show(getFragmentManager(), AdicionarClienteFragment.BOTTOM_SHEET_FRAGMENT_TAG);
    }
}
