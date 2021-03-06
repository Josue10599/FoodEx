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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.R;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.ui.image.ImageLoader;
import com.fulltime.foodex.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {
    private List<Cliente> clientesSalvos = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Context context;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View viewHolderCliente = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(viewHolderCliente);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        holder.bindView(clientesSalvos.get(position));
    }

    @Override
    public int getItemCount() {
        return clientesSalvos.size();
    }

    public Cliente getCliente(int posicao) {
        return clientesSalvos.get(posicao);
    }

    public void insereCliente(Cliente novoCliente) {
        if (!clientesSalvos.contains(novoCliente)) {
            clientesSalvos.add(novoCliente);
            notifyItemInserted(clientesSalvos.size() - 1);
        } else alteraCliente(novoCliente);
    }

    private void alteraCliente(Cliente clienteAlterado) {
        int posicao = clientesSalvos.indexOf(clienteAlterado);
        clientesSalvos.set(posicao, clienteAlterado);
        notifyItemChanged(posicao, clienteAlterado);
    }

    public void setLista(List<Cliente> clientes) {
        clientesSalvos = clientes;
        notifyDataSetChanged();
    }

    public void removeCliente(int posicao) {
        clientesSalvos.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void movimentaCliente(int posicaoInicial, int posicaoFinal) {
        Collections.swap(clientesSalvos, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    public void insereCliente(Cliente cliente, int posicao) {
        clientesSalvos.add(posicao, cliente);
        notifyItemInserted(posicao);
    }

    class ClienteViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagemCliente;
        private final TextView nomeCliente;
        private final TextView devendoCliente;
        private Cliente cliente;

        ClienteViewHolder(@NonNull final View itemView) {
            super(itemView);
            imagemCliente = itemView.findViewById(R.id.item_cliente_foto);
            nomeCliente = itemView.findViewById(R.id.item_cliente_nome);
            devendoCliente = itemView.findViewById(R.id.item_cliente_valor);
            if (onItemClickListener != null)
                itemView.setOnClickListener(v -> onItemClickListener.onItemClickListener(getAdapterPosition(), cliente));
        }

        void bindView(@NonNull Cliente cliente) {
            this.cliente = cliente;
            imagemCliente.setImageDrawable(new ImageLoader(context).getDrawableCliente(cliente));
            nomeCliente.setText(cliente.nomeCompleto());
            devendoCliente.setText(context.getString(R.string.sifra, cliente.getValorEmDeficit()));
            if (cliente.estaDevendo())
                devendoCliente.setTextColor(context.getResources().getColor(R.color.color_nao_pago));
            else devendoCliente.setTextColor(context.getResources().getColor(R.color.color_pago));
        }
    }
}
