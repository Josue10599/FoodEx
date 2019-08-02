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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class DetalhesVendaFragment extends BottomSheetDialogFragment {
    private final Venda venda;

    public DetalhesVendaFragment(Venda venda) {
        this.venda = venda;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewDetalhesVenda = inflater.inflate(R.layout.fragment_bottom_sheet_detalhes_venda, container, false);
        bind(viewDetalhesVenda);
        return viewDetalhesVenda;
    }

    private void bind(View viewDetalhesVenda) {
        MaterialTextView dataVenda = viewDetalhesVenda.findViewById(R.id.fragment_detalhes_venda_data_compra);
        MaterialTextView nomeProduto = viewDetalhesVenda.findViewById(R.id.fragment_detalhes_venda_nome_produto);
        MaterialTextView quantidadeProduto = viewDetalhesVenda.findViewById(R.id.fragment_detalhes_venda_quantidade_produto);
        MaterialTextView valorVenda = viewDetalhesVenda.findViewById(R.id.fragment_detalhes_venda_valor_produto);
        MaterialTextView descricaoProduto = viewDetalhesVenda.findViewById(R.id.fragment_detalhes_venda_descricao_produto);
        MaterialTextView nomeCliente = viewDetalhesVenda.findViewById(R.id.fragment_detalhes_venda_nome_cliente);
        MaterialTextView estadoPagamento = viewDetalhesVenda.findViewById(R.id.fragment_detalhes_venda_estado_pagamento);
        Produto produtoVendido = venda.getProdutoVendido();
        Cliente clienteComprador = venda.getCliente();
        dataVenda.setText(venda.dataVendaFormatada());
        nomeProduto.setText(produtoVendido.getNome());
        quantidadeProduto.setText(String.valueOf(venda.getQuantidade()));
        descricaoProduto.setText(produtoVendido.getDescricao());
        valorVenda.setText(getString(R.string.sifra, venda.valorDaCompraFormatado()));
        nomeCliente.setText(getString(R.string.acquired_by, clienteComprador.nomeCompleto()));
        if (venda.getPago()) {
            estadoPagamento.setText(getString(R.string.paid_at_time_of_purchase));
            valorVenda.setTextColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.color_pago));
        } else {
            estadoPagamento.setText(getString(R.string.sale_made_without_payment));
            valorVenda.setTextColor(Objects.requireNonNull(getContext()).getResources().getColor(R.color.color_nao_pago));
        }
    }
}
