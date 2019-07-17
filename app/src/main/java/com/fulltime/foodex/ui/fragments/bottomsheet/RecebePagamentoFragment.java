package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.update.ListaCliente;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.mask.MoneyMaskWatcher;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.searchablespinner.SearchableSpinnerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class RecebePagamentoFragment extends BottomSheetDialogFragment {
    private Cliente clienteSelecionado;
    private String valorPago;
    private TextInputLayout textInputLayoutCampoValor;
    private EditText editTextCampoValor;

    private final ArrayList<Object> clientesDevedores = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        UpdateData.listaClientes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetRecebePagamento = inflater.
                inflate(R.layout.fragment_bottom_sheet_add_pagamento, container, false);
        configuraSearchableSpinnerCliente(bottomSheetRecebePagamento);
        configuraCampoValorRecebido(bottomSheetRecebePagamento);
        configuraBotaoConfirmarRecebimento(bottomSheetRecebePagamento);
        return bottomSheetRecebePagamento;
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
    }

    private void configuraBotaoConfirmarRecebimento(View bottomSheetRecebePagamento) {
        MaterialButton buttonReceberPagamento = bottomSheetRecebePagamento
                .findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonReceberPagamento.setOnClickListener(v -> {
            getValorDigitadoCampoValorRecebido();
            if (clienteSelecionado != null && !valorPago.isEmpty()) {
                clienteSelecionado.valorPago(valorPago);
                UpdateData.atualizaCliente(clienteSelecionado);
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
        spinnerCliente.setAdapter(new SearchableSpinnerAdapter(getContext(), clientesDevedores));
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
}
