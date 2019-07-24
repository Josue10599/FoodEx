package com.fulltime.foodex.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fulltime.foodex.R;
import com.fulltime.foodex.model.Cliente;
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
        notifyItemRemoved(posicao);
        clientesSalvos.remove(posicao);
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
        private final TextView telefoneCliente;
        private final TextView devendoCliente;
        private Cliente cliente;

        ClienteViewHolder(@NonNull final View itemView) {
            super(itemView);
            imagemCliente = itemView.findViewById(R.id.item_cliente_foto);
            nomeCliente = itemView.findViewById(R.id.item_cliente_nome);
            telefoneCliente = itemView.findViewById(R.id.item_cliente_telefone);
            devendoCliente = itemView.findViewById(R.id.item_cliente_valor);
            itemView.setOnClickListener(v ->
                    onItemClickListener.onItemClickListener(getAdapterPosition(), cliente));
        }

        void bindView(@NonNull Cliente cliente) {
            this.cliente = cliente;
            imagemCliente.setImageDrawable(TextDrawable.builder()
                    .buildRound(cliente.primeiraLetraNome()
                                    + cliente.primeiraLetraSobrenome(),
                            context.getResources().getColor(R.color.color_secondary)));
            nomeCliente.setText(cliente.nomeCompleto());
            telefoneCliente.setText(cliente.getTelefone());
            devendoCliente.setText(context.getString(R.string.sifra, cliente.getValorEmDeficit()));
            if (cliente.estaDevendo())
                devendoCliente.setTextColor(context.getResources().getColor(R.color.color_nao_pago));
            else devendoCliente.setTextColor(context.getResources().getColor(R.color.color_pago));
        }
    }
}
