package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
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
                new ImplementaVendaFragment(new ImplementaVendaFragment.VendaImplementadaListener() {
                    @Override
                    public void vendaConcluida(Venda venda, Cliente clienteQueComprou) {
                        FirestoreAdapter.build().setCliente(clienteQueComprou);
                        FirestoreAdapter.build().setVenda(venda);
                    }
                }).show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }

    private void configurarBotaoAddPagamento(View bottomSheetMenuOpcoes) {
        MaterialButton botaoAddPagamento = bottomSheetMenuOpcoes
                .findViewById(R.id.bottom_sheet_menu_options_botao_add_pagamento);
        botaoAddPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Diminuir o saldo devedor dos clientes
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
                new ImplementaProdutoFragment(new ImplementaProdutoFragment.ProdutoImplementadoListener() {
                    @Override
                    public void produtoSalvo(Produto produto) {
                        //Adicionar na Lista do RecyclerView e mandar para o Firebase
                        FirestoreAdapter.build().setProduto(produto);
                    }
                }).show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
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
                new ImplementaClienteFragment(new ImplementaClienteFragment.ClienteImplementadoListener() {
                    @Override
                    public void clienteImplementado(Cliente cliente) {
                        //Adicionar na Lista do RecyclerView e mandar para o Firebase
                        FirestoreAdapter.build().setCliente(cliente);
                    }
                }).show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }
}
