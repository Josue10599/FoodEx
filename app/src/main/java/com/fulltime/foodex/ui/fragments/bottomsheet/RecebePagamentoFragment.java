package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.firebase.firestore.OnQueryListener;
import com.fulltime.foodex.mask.MoneyMaskWatcher;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.searchablespinner.SearchableSpinnerAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class RecebePagamentoFragment extends BottomSheetDialogFragment {

    private PagementoRecebidoListener pagementoRecebidoListener;

    private Cliente clienteSelecionado;
    private String valorPago;
    private TextInputLayout textInputLayoutCampoValor;
    private EditText editTextCampoValor;

    public RecebePagamentoFragment(PagementoRecebidoListener pagementoRecebidoListener) {
        this.pagementoRecebidoListener = pagementoRecebidoListener;
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

    private void configuraBotaoConfirmarRecebimento(View bottomSheetRecebePagamento) {
        MaterialButton buttonReceberPagamento = bottomSheetRecebePagamento
                .findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonReceberPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValorDigitadoCampoValorRecebido();
                if (clienteSelecionado != null && valorPago.isEmpty()) {
                    pagementoRecebidoListener.pagamentoRecebido(clienteSelecionado, valorPago);
                    dismiss();
                }
            }
        });
    }

    private void getValorDigitadoCampoValorRecebido() {
        String valorDigitado = editTextCampoValor.getText().toString();
        if (!valorDigitado.equals("0,00")) {
            valorPago = valorDigitado;
        } else {
            textInputLayoutCampoValor.setError(getContext().getText(R.string.error_campo_obrigatorio));
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
        final ArrayList<Object> clientes = new ArrayList<>();
        FirestoreAdapter.build().getCliente(new OnQueryListener() {
            @Override
            public void onSucessful(Object cliente) {
                if (((Cliente) cliente).estaDevendo()) clientes.add(cliente);
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

    interface PagementoRecebidoListener {
        void pagamentoRecebido(Cliente clienteQuePagou, String valorPago);
    }

}
