package com.fulltime.foodex.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.firebase.firestore.OnQueryListener;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.ui.fragments.bottomsheet.AdicionarClienteFragment;
import com.fulltime.foodex.ui.recyclerview.adapter.ClientesAdapter;
import com.fulltime.foodex.ui.recyclerview.adapter.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fulltime.foodex.ui.fragments.bottomsheet.Constantes.BOTTOM_SHEET_FRAGMENT_TAG;

public class ClientesFragment extends Fragment {

    private ClientesAdapter adapter;
    private List<Cliente> todosClientes;
    private List<Cliente> devedores;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View clientView = inflater.inflate(R.layout.fragment_cliente, container, false);
        todosClientes = new ArrayList<>();
        devedores = filtraDevedores(todosClientes);
        configuraTabMenu(clientView);
        configuraAdapter();
        configuraRecyclerView(clientView);
        return clientView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirestoreAdapter.build().getClientes(new OnQueryListener() {
            @Override
            public void onSucessful(List item) {
                for (Object cliente : item) adapter.adicionaCliente((Cliente) cliente);
            }
        });
    }

    private void configuraTabMenu(View clientView) {
        TabLayout tabLayoutMenuCliente = clientView.findViewById(R.id.fragment_cliente_tab_menu);
        tabLayoutMenuCliente.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (Objects.requireNonNull(tab.getText()).toString()
                        .equals(Objects.requireNonNull(getContext())
                                .getString(R.string.fragment_cliente_devedores)))
                    adapter.alteraLista(devedores);
                else adapter.alteraLista(todosClientes);
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
        adapter = new ClientesAdapter(todosClientes);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(final int posicao, Object clienteSelecionado) {
                assert getFragmentManager() != null;
                new AdicionarClienteFragment((Cliente) clienteSelecionado, new AdicionarClienteFragment.ClienteImplementado() {
                    @Override
                    public void clienteImplementado(Cliente cliente) {
                        adapter.alteraCliente(posicao, cliente);
                        FirestoreAdapter.build().setCliente(cliente);
                    }
                }).show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }

    private void configuraRecyclerView(View clientView) {
        RecyclerView listaCliente = clientView.findViewById(R.id.fragment_cliente_recycler_view);
        listaCliente.setAdapter(adapter);
    }

    private List<Cliente> filtraDevedores(List<Cliente> clientes) {
        final List<Cliente> listaFiltrada = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.estaDevendo()) listaFiltrada.add(cliente);
        }
        return listaFiltrada;
    }
}
