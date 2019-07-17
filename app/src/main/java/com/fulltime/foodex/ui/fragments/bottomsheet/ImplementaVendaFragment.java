package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.update.ListaCliente;
import com.fulltime.foodex.helper.update.ListaProduto;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.searchablespinner.SearchableSpinnerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class ImplementaVendaFragment extends BottomSheetDialogFragment {
    private TextView textViewQuantidade;
    private Cliente clienteSelecionado;
    private Produto produtoSelecionado;
    private boolean vendaPaga;
    private int quantidade = 1;
    private final ArrayList<Produto> produtosCadastrados = new ArrayList<>();
    private final ArrayList<Cliente> clientesCadastrados = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        UpdateData.listaClientes();
        UpdateData.listaProdutos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View bottomSheetAdicionarVenda = inflater.
                inflate(R.layout.fragment_bottom_sheet_add_venda, container, false);
        bindCampos(bottomSheetAdicionarVenda);
        return bottomSheetAdicionarVenda;
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
    public void getListaClientes(ListaCliente listaCliente) {
        clientesCadastrados.clear();
        clientesCadastrados.addAll(listaCliente.getClientes());
    }

    @Subscribe
    public void getListaProdutos(ListaProduto listaProduto) {
        produtosCadastrados.clear();
        produtosCadastrados.addAll(listaProduto.getProdutos());
    }

    private void bindCampos(View bottomSheetAdicionarVenda) {
        configuraSearchableSpinnerCliente(bottomSheetAdicionarVenda);
        configuraSearchableSpinnerProduto(bottomSheetAdicionarVenda);
        configuraTextViewQuantidade(bottomSheetAdicionarVenda);
        configuraBotaoIncrementa(bottomSheetAdicionarVenda);
        configuraBotaoDecrementa(bottomSheetAdicionarVenda);
        configuraSwitchEstadoVenda(bottomSheetAdicionarVenda);
        configuraBotaoCadastrar(bottomSheetAdicionarVenda);
    }

    private void configuraSwitchEstadoVenda(View bottomSheetAdicionarVenda) {
        Switch switchEstadoVenda = bottomSheetAdicionarVenda.findViewById(R.id.bottom_sheet_add_venda_switch_estado);
        switchEstadoVenda.setOnCheckedChangeListener((buttonView, isChecked) -> vendaPaga = isChecked);
    }

    private void configuraBotaoCadastrar(View bottomSheetAdicionarVenda) {
        MaterialButton buttonCadastrar = bottomSheetAdicionarVenda.findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonCadastrar.setOnClickListener(v -> {
            if (clienteSelecionado != null && produtoSelecionado != null) {
                Venda venda = new Venda(clienteSelecionado, vendaPaga, produtoSelecionado, quantidade);
                UpdateData.atualizaCliente(clienteSelecionado);
                UpdateData.atualizaVenda(venda);
                dismiss();
            }
        });
    }

    private void configuraSearchableSpinnerProduto(View bottomSheetAdicionarVenda) {
        final SearchableSpinner spinnerProduto = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_searchspinner_produto);
        spinnerProduto.setAdapter(new SearchableSpinnerAdapter(getContext(), produtosCadastrados));
        spinnerProduto.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                produtoSelecionado = (Produto) spinnerProduto.getSelectedItem();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void configuraSearchableSpinnerCliente(final View bottomSheetAdicionarVenda) {
        final SearchableSpinner spinnerCliente = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_searchspinner_cliente);
        spinnerCliente.setAdapter(new SearchableSpinnerAdapter(getContext(), clientesCadastrados));
        spinnerCliente.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                clienteSelecionado = (Cliente) spinnerCliente.getSelectedItem();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void configuraTextViewQuantidade(View bottomSheetAdicionarVenda) {
        textViewQuantidade = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_quantidade);
        textViewQuantidade.setText(String.valueOf(quantidade));
    }

    private void configuraBotaoIncrementa(View bottomSheetAdicionarVenda) {
        Button botaoIncrementa = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_incrementar_quantidade);
        botaoIncrementa.setOnClickListener(v -> {
            if (quantidade < 1000) quantidade++;
            textViewQuantidade.setText(String.valueOf(quantidade));
        });
    }

    private void configuraBotaoDecrementa(View bottomSheetAdicionarVenda) {
        Button botaoDecrementa = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_decrementar_quantidade);
        botaoDecrementa.setOnClickListener(v -> {
            if (quantidade > 1) quantidade--;
            textViewQuantidade.setText(String.valueOf(quantidade));
        });
    }
}
