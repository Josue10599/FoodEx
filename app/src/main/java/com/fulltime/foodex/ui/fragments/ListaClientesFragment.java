package com.fulltime.foodex.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.update.ListaCliente;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.ui.fragments.bottomsheet.ImplementaClienteFragment;
import com.fulltime.foodex.ui.recyclerview.adapter.ClienteAdapter;
import com.fulltime.foodex.ui.recyclerview.callback.ItemTouchCallback;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class ListaClientesFragment extends Fragment {

    private final ClienteAdapter clienteAdapter = new ClienteAdapter();

    private SwipeRefreshLayout swipe;

    private List<Cliente> todosClientes = new ArrayList<>();
    private List<Cliente> devedores = new ArrayList<>();
    private TabLayout tabLayoutMenuCliente;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View clientView = inflater.inflate(R.layout.fragment_cliente, container, false);
        configuraSwipe(clientView);
        configuraTabMenu(clientView);
        configuraRecyclerView(clientView);
        UpdateData.listaTodosClientes();
        return clientView;
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
    public void onCreateCliente(Cliente cliente) {
        swipe.setRefreshing(false);
        if (allClients()) {
            todosClientes.add(cliente);
            clienteAdapter.insereCliente(cliente);
        }
    }

    @Subscribe
    public void onGetListaClientes(ListaCliente listaCliente) {
        atualizaListas(listaCliente);
    }

    private void atualizaListas(@NonNull ListaCliente listaCliente) {
        List<Cliente> clientes = listaCliente.getClientes();
        if (clientes.size() != 0) swipe.setRefreshing(false);
        todosClientes = clientes;
        devedores = filtraDevedores(todosClientes);
        if (allClients()) clienteAdapter.setLista(todosClientes);
        else clienteAdapter.setLista(devedores);
    }

    private boolean allClients() {
        return tabLayoutMenuCliente.getSelectedTabPosition() == 0;
    }

    private void configuraSwipe(View view) {
        swipe = view.findViewById(R.id.swipe_refresh);
        swipe.setRefreshing(true);
        swipe.setOnRefreshListener(UpdateData::listaTodosClientes);
    }

    private void configuraTabMenu(View clientView) {
        tabLayoutMenuCliente = clientView.findViewById(R.id.fragment_cliente_tab_menu);
        tabLayoutMenuCliente.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1)
                    clienteAdapter.setLista(devedores);
                else clienteAdapter.setLista(todosClientes);
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
        clienteAdapter.setOnItemClickListener((posicao, clienteSelecionado) -> {
            assert getFragmentManager() != null;
            new ImplementaClienteFragment((Cliente) clienteSelecionado)
                    .show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
        });
    }

    private void configuraRecyclerView(View clientView) {
        configuraAdapter();
        RecyclerView listaCliente = clientView.findViewById(R.id.fragment_cliente_recycler_view);
        listaCliente.setAdapter(clienteAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchCallback());
        itemTouchHelper.attachToRecyclerView(listaCliente);
    }

    private List<Cliente> filtraDevedores(List<Cliente> clientes) {
        final List<Cliente> listaFiltrada = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.estaDevendo()) listaFiltrada.add(cliente);
        }
        return listaFiltrada;
    }
}
