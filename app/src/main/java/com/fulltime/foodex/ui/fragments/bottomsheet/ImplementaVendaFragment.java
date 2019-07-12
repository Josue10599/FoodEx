package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.firebase.firestore.OnQueryListener;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.searchablespinner.SearchableSpinnerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class ImplementaVendaFragment extends BottomSheetDialogFragment {

    private final VendaImplementadaListener vendaImplementadaListener;
    private TextView textViewQuantidade;
    private Cliente clienteSelecionado;
    private Produto produtoSelecionado;
    private boolean vendaPaga;
    private int quantidade = 1;

    public ImplementaVendaFragment(VendaImplementadaListener vendaImplementadaListener) {
        this.vendaImplementadaListener = vendaImplementadaListener;
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
        switchEstadoVenda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vendaPaga = isChecked;
            }
        });
    }

    private void configuraBotaoCadastrar(View bottomSheetAdicionarVenda) {
        MaterialButton buttonCadastrar = bottomSheetAdicionarVenda.findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clienteSelecionado != null && produtoSelecionado != null) {
                    List<Produto> produtos = new ArrayList<>();
                    for (int i = 0; i < quantidade; i++) produtos.add(produtoSelecionado);
                    Venda venda = new Venda(clienteSelecionado, vendaPaga, produtos);
                    vendaImplementadaListener.vendaConcluida(venda, clienteSelecionado);
                    dismiss();
                }
            }
        });
    }

    private void configuraSearchableSpinnerProduto(View bottomSheetAdicionarVenda) {
        final SearchableSpinner spinnerProduto = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_searchspinner_produto);
        final ArrayList<Object> produtos = new ArrayList<>();
        FirestoreAdapter.build().getProdutos(new OnQueryListener() {
            @Override
            public void onSucessful(Object produto) {
                produtos.add(produto);
            }
        });
        spinnerProduto.setAdapter(new SearchableSpinnerAdapter(getContext(), produtos));
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
        final ArrayList<Object> clientes = new ArrayList<>();
        FirestoreAdapter.build().getCliente(new OnQueryListener() {
            @Override
            public void onSucessful(Object cliente) {
                clientes.add(cliente);
            }
        });
        spinnerCliente.setAdapter(new SearchableSpinnerAdapter(getContext(), clientes));
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
        botaoIncrementa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantidade < 1000) quantidade++;
                textViewQuantidade.setText(String.valueOf(quantidade));
            }
        });
    }

    private void configuraBotaoDecrementa(View bottomSheetAdicionarVenda) {
        Button botaoDecrementa = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_decrementar_quantidade);
        botaoDecrementa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantidade > 1) quantidade--;
                textViewQuantidade.setText(String.valueOf(quantidade));
            }
        });
    }

    public interface VendaImplementadaListener {
        void vendaConcluida(Venda venda, Cliente clienteQueComprou);
    }
}
