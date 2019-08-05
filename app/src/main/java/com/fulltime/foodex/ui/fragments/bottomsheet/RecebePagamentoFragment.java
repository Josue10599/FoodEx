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
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.eventbus.ListaCliente;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.mask.MoneyMaskWatcher;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Pagamento;
import com.fulltime.foodex.searchablespinner.SearchableSpinnerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class RecebePagamentoFragment extends BottomSheetDialogFragment {
    private final ArrayList<Object> clientesDevedores = new ArrayList<>();
    private Cliente clienteSelecionado;
    private String valorPago;
    private TextInputLayout textInputLayoutCampoValor;
    private EditText editTextCampoValor;
    private SearchableSpinnerAdapter adapterClientes;
    private MaterialButton buttonReceberPagamento;
    private MaterialTextView textViewValorEmDeficit;
    private MaterialTextView textViewMensagemValorEmDeficit;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        adapterClientes = new SearchableSpinnerAdapter(context, clientesDevedores);
        UpdateData.listaTodosClientes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetRecebePagamento = inflater.
                inflate(R.layout.fragment_bottom_sheet_add_pagamento, container, false);
        configuraSearchableSpinnerCliente(bottomSheetRecebePagamento);
        configuraCampoValorRecebido(bottomSheetRecebePagamento);
        configuraCampoValorEmDeficit(bottomSheetRecebePagamento);
        configuraBotaoConfirmarRecebimento(bottomSheetRecebePagamento);
        return bottomSheetRecebePagamento;
    }

    private void configuraCampoValorEmDeficit(View bottomSheetRecebePagamento) {
        textViewMensagemValorEmDeficit =
                bottomSheetRecebePagamento.findViewById(R.id.bottom_sheet_add_pagamento_mensagem_valor);
        textViewValorEmDeficit =
                bottomSheetRecebePagamento.findViewById(R.id.bottom_sheet_add_pagamento_valor_devido_cliente);
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
    public void getListaCliente(ListaCliente listaCliente) {
        for (Cliente cliente : listaCliente.getClientes())
            if (cliente.estaDevendo() && !clientesDevedores.contains(cliente))
                clientesDevedores.add(cliente);
        adapterClientes.setList(clientesDevedores);
    }

    private void configuraBotaoConfirmarRecebimento(View bottomSheetRecebePagamento) {
        buttonReceberPagamento = bottomSheetRecebePagamento
                .findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonReceberPagamento.setOnClickListener(v -> {
            getValorDigitadoCampoValorRecebido();
            if (clienteSelecionado != null && !valorPago.isEmpty()) {
                clienteSelecionado.valorPago(valorPago);
                UpdateData.atualizaCliente(clienteSelecionado);
                UpdateData.atualizaPagamento(new Pagamento(clienteSelecionado, valorPago));
                dismiss();
            }
        });
    }

    private void getValorDigitadoCampoValorRecebido() {
        String valorDigitado = editTextCampoValor.getText().toString();
        if (!valorDigitado.equals("0,00")) {
            valorPago = valorDigitado;
        } else {
            textInputLayoutCampoValor.setError(Objects.requireNonNull(getContext())
                    .getText(R.string.error_campo_obrigatorio));
        }
    }

    private void configuraCampoValorRecebido(View bottomSheetRecebePagamento) {
        textInputLayoutCampoValor = bottomSheetRecebePagamento
                .findViewById(R.id.bottom_sheet_add_pagamento_valor_recebido);
        editTextCampoValor = Objects.requireNonNull(textInputLayoutCampoValor.getEditText());
        editTextCampoValor.setOnFocusChangeListener(MoneyMaskWatcher.buildMoney());
        editTextCampoValor.addTextChangedListener(MoneyMaskWatcher.buildMoney());
    }

    private void configuraSearchableSpinnerCliente(final View bottomSheetAdicionarVenda) {
        final SearchableSpinner spinnerCliente = bottomSheetAdicionarVenda.
                findViewById(R.id.bottom_sheet_add_venda_searchspinner_cliente);
        spinnerCliente.setAdapter(adapterClientes);
        spinnerCliente.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                buttonReceberPagamento.setEnabled(true);
                clienteSelecionado = (Cliente) spinnerCliente.getSelectedItem();
                textViewMensagemValorEmDeficit.setVisibility(View.VISIBLE);
                textViewValorEmDeficit.setVisibility(View.VISIBLE);
                textViewValorEmDeficit.setText(String.format(getString(R.string.sifra),
                        clienteSelecionado.getValorEmDeficit()));
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
}
