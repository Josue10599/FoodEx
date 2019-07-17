package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class MenuOpcoesFragments extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetMenuOpcoes = inflater
                .inflate(R.layout.fragment_bottom_sheet_menu_options, container, false);
        configurarBotaoAddCliente(bottomSheetMenuOpcoes);
        configurarBotaoAddProduto(bottomSheetMenuOpcoes);
        configurarBotaoAddPagamento(bottomSheetMenuOpcoes);
        getConfigurarBotaoAddVenda(bottomSheetMenuOpcoes);
        return bottomSheetMenuOpcoes;
    }

    private void getConfigurarBotaoAddVenda(View bottomSheetMenuOpcoes) {
        MaterialButton botaoAddVenda = bottomSheetMenuOpcoes
                .findViewById(R.id.bottom_sheet_menu_options_botao_vender_produto);
        botaoAddVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                new ImplementaVendaFragment().show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }

    private void configurarBotaoAddPagamento(View bottomSheetMenuOpcoes) {
        MaterialButton botaoAddPagamento = bottomSheetMenuOpcoes
                .findViewById(R.id.bottom_sheet_menu_options_botao_add_pagamento);
        botaoAddPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                new RecebePagamentoFragment().show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }

    private void configurarBotaoAddProduto(View bottomSheetMenuOpcoes) {
        MaterialButton botaoAddProduto = bottomSheetMenuOpcoes
                .findViewById(R.id.bottom_sheet_menu_options_botao_add_produto);
        botaoAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                new ImplementaProdutoFragment().show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }

    private void configurarBotaoAddCliente(View bottomSheetMenuOpcoes) {
        MaterialButton botaoAddCliente = bottomSheetMenuOpcoes
                .findViewById(R.id.bottom_sheet_menu_options_botao_add_cliente);
        botaoAddCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                new ImplementaClienteFragment().show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }
}
