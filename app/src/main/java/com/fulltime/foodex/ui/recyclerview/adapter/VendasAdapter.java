package com.fulltime.foodex.ui.recyclerview.adapter;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.R;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class VendasAdapter extends RecyclerView.Adapter<VendasAdapter.VendasViewHolder> {

    private List<Venda> vendasRealizadas = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public VendasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new VendasViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_venda, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VendasViewHolder holder, int position) {
        holder.bindViewHolder(vendasRealizadas.get(position));
    }

    @Override
    public int getItemCount() {
        return vendasRealizadas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void adicionaVenda(Venda venda) {
        if (!vendasRealizadas.contains(venda)) {
            vendasRealizadas.add(venda);
            notifyItemInserted(0);
        }
    }

    public void setLista(List<Venda> vendas) {
        vendasRealizadas = vendas;
        notifyDataSetChanged();
    }

    class VendasViewHolder extends RecyclerView.ViewHolder {

        final TextView dataVenda;
        final TextView nomeClienteComprador;
        final TextView nomeProdutoVendido;
        final TextView valorDaVenda;
        Venda venda;

        VendasViewHolder(@NonNull View itemView) {
            super(itemView);
            dataVenda = itemView.findViewById(R.id.item_venda_data_venda);
            nomeClienteComprador = itemView.findViewById(R.id.item_venda_nome_cliente_comprador);
            nomeProdutoVendido = itemView.findViewById(R.id.item_venda_nome_e_quantidade_produto_vendido);
            valorDaVenda = itemView.findViewById(R.id.item_venda_valor_da_venda);
            if (onItemClickListener != null)
                itemView.setOnClickListener(v -> onItemClickListener.onItemClickListener(getAdapterPosition(), venda));
        }

        void bindViewHolder(Venda venda) {
            this.venda = venda;
            Cliente comprador = venda.getCliente();
            Produto produtoComprado = venda.getProdutoVendido();
            dataVenda.setText(venda.dataVendaFormatada());
            nomeClienteComprador.setText(comprador.nomeCompleto());
            nomeProdutoVendido.setText(context.getString(R.string.quantity_and_product,
                    venda.getQuantidade(), produtoComprado.getNome()));
            valorDaVenda.setText(context.getString(R.string.sifra, venda.valorDaCompraFormatado()));
            if (venda.getPago())
                valorDaVenda.setTextColor(context.getResources().getColor(R.color.color_pago));
            else valorDaVenda.setTextColor(context.getResources().getColor(R.color.color_nao_pago));
        }
    }
}
