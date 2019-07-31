package com.fulltime.foodex.ui.fragments.bottomsheet;

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

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.formatter.FormataDinheiro;
import com.fulltime.foodex.helper.update.ListaCliente;
import com.fulltime.foodex.helper.update.ListaProduto;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.searchablespinner.SearchableSpinnerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

import static android.view.View.VISIBLE;

public class ImplementaVendaFragment extends BottomSheetDialogFragment {
    private static final int MIN_QTD = 1;
    private static final int MAX_QTD = 1000;
    private final ArrayList<Produto> produtosCadastrados = new ArrayList<>();
    private final ArrayList<Cliente> clientesCadastrados = new ArrayList<>();

    private boolean vendaPaga;
    private int quantidade = 1;

    private Cliente clienteSelecionado;
    private Produto produtoSelecionado;

    private SearchableSpinner spinnerCliente;
    private SearchableSpinner spinnerProduto;
    private SearchableSpinnerAdapter adapterProdutos;
    private SearchableSpinnerAdapter adapterClientes;
    private Button botaoIncrementa;
    private MaterialTextView textViewQuantidade;
    private Button botaoDecrementa;
    private MaterialTextView textViewValorVenda;
    private Switch switchEstadoVenda;
    private MaterialButton buttonCadastrar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        UpdateData.listaTodosClientes();
        UpdateData.listaProdutos();
        adapterClientes = new SearchableSpinnerAdapter(context, clientesCadastrados);
        adapterProdutos = new SearchableSpinnerAdapter(context, produtosCadastrados);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetAdicionarVenda = inflater.inflate(R.layout.fragment_bottom_sheet_add_venda, container, false);
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
        adapterClientes.setList(clientesCadastrados);
    }

    @Subscribe
    public void getListaProdutos(ListaProduto listaProduto) {
        produtosCadastrados.clear();
        produtosCadastrados.addAll(listaProduto.getProdutos());
        adapterProdutos.setList(produtosCadastrados);
    }

    private void bindCampos(View bottomSheetAdicionarVenda) {
        configuraSearchableSpinnerCliente(bottomSheetAdicionarVenda);
        configuraSearchableSpinnerProduto(bottomSheetAdicionarVenda);
        configuraTextViewQuantidade(bottomSheetAdicionarVenda);
        configuraTextViewValor(bottomSheetAdicionarVenda);
        configuraBotaoIncrementa(bottomSheetAdicionarVenda);
        configuraBotaoDecrementa(bottomSheetAdicionarVenda);
        configuraSwitchEstadoVenda(bottomSheetAdicionarVenda);
        configuraBotaoCadastrar(bottomSheetAdicionarVenda);
        toogleButton();
    }

    private void configuraBotaoCadastrar(View bottomSheetAdicionarVenda) {
        buttonCadastrar = bottomSheetAdicionarVenda.findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonCadastrar.setOnClickListener(v -> {
            if (clienteEProdutoSelecionado()) {
                UpdateData.atualizaVenda(new Venda(clienteSelecionado, vendaPaga, produtoSelecionado, quantidade));
                UpdateData.atualizaCliente(clienteSelecionado);
                dismiss();
            }
        });
    }

    private void configuraTextViewValor(View bottomSheetAdicionarVenda) {
        textViewValorVenda = bottomSheetAdicionarVenda.findViewById(R.id.bottom_sheet_add_venda_valor);
        calculaValor();
    }

    private void configuraSwitchEstadoVenda(View bottomSheetAdicionarVenda) {
        switchEstadoVenda = bottomSheetAdicionarVenda.findViewById(R.id.bottom_sheet_add_venda_switch_estado);
        switchEstadoVenda.setOnCheckedChangeListener((buttonView, isChecked) -> vendaPaga = isChecked);
    }

    private void configuraSearchableSpinnerProduto(View bottomSheetAdicionarVenda) {
        spinnerProduto = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_searchspinner_produto);
        spinnerProduto.setAdapter(adapterProdutos);
        spinnerProduto.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                produtoSelecionado = (Produto) spinnerProduto.getSelectedItem();
                clienteEProdutoSelecionado();
                calculaValor();
            }

            @Override
            public void onNothingSelected() {
            }
        });
        spinnerProduto.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {
                spinnerCliente.hideEdit();
            }

            @Override
            public void spinnerIsClosing() {

            }
        });
    }

    private void configuraSearchableSpinnerCliente(final View bottomSheetAdicionarVenda) {
        spinnerCliente = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_searchspinner_cliente);
        spinnerCliente.setAdapter(adapterClientes);
        spinnerCliente.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                clienteSelecionado = (Cliente) spinnerCliente.getSelectedItem();
                clienteEProdutoSelecionado();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        spinnerCliente.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {
                spinnerProduto.hideEdit();
            }

            @Override
            public void spinnerIsClosing() {

            }
        });
    }

    private void configuraTextViewQuantidade(View bottomSheetAdicionarVenda) {
        textViewQuantidade = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_quantidade);
        textViewQuantidade.setText(String.valueOf(quantidade));
    }

    private void configuraBotaoIncrementa(View bottomSheetAdicionarVenda) {
        botaoIncrementa = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_incrementar_quantidade);
        botaoIncrementa.setOnClickListener(v -> {
            if (quantidade < MAX_QTD) quantidade++;
            textViewQuantidade.setText(String.valueOf(quantidade));
            calculaValor();
            toogleButton();
        });
    }

    private void configuraBotaoDecrementa(View bottomSheetAdicionarVenda) {
        botaoDecrementa = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_decrementar_quantidade);
        botaoDecrementa.setOnClickListener(v -> {
            if (quantidade > MIN_QTD) quantidade--;
            textViewQuantidade.setText(String.valueOf(quantidade));
            calculaValor();
            toogleButton();
        });
    }

    private void toogleButton() {
        if (quantidade == MIN_QTD) botaoDecrementa.setEnabled(false);
        else botaoDecrementa.setEnabled(true);
        if (quantidade == MAX_QTD) botaoIncrementa.setEnabled(false);
        else botaoIncrementa.setEnabled(true);
    }

    private void calculaValor() {
        FormataDinheiro formataDinheiro = new FormataDinheiro();
        BigDecimal valor;
        if (produtoSelecionado != null)
            valor = formataDinheiro.getBigDecimal(produtoSelecionado.getValor());
        else valor = formataDinheiro.getBigDecimal("0");
        valor = valor.multiply(BigDecimal.valueOf(quantidade));
        String textoFormatado = String.format(getString(R.string.sifra), formataDinheiro.formataValor(valor));
        textViewValorVenda.setText(textoFormatado);
    }

    private boolean clienteEProdutoSelecionado() {
        if (clienteSelecionado != null && produtoSelecionado != null) {
            botaoIncrementa.setVisibility(VISIBLE);
            textViewQuantidade.setVisibility(VISIBLE);
            botaoDecrementa.setVisibility(VISIBLE);
            textViewValorVenda.setVisibility(VISIBLE);
            switchEstadoVenda.setVisibility(VISIBLE);
            buttonCadastrar.setEnabled(true);
            return true;
        }
        return false;
    }
}
