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
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.fulltime.foodex.R;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.Collections;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {
    private List<Cliente> listaClientes;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public ClienteAdapter(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

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
        holder.bindView(listaClientes.get(position));
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public void adicionaCliente(Cliente novoCliente) {
        if (!listaClientes.contains(novoCliente)) {
            listaClientes.add(novoCliente);
            notifyItemInserted(listaClientes.size() - 1);
        }
    }

    public void alteraCliente(int posicao, Cliente clienteAlterado) {
        listaClientes.set(posicao, clienteAlterado);
        notifyItemChanged(posicao);
    }

    public void alteraLista(List<Cliente> clientes) {
        listaClientes = clientes;
        notifyDataSetChanged();
    }

    public void removeCliente(int posicao) {
        listaClientes.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void movimentaCliente(int posicaoInicial, int posicaoFinal) {
        Collections.swap(listaClientes, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(getAdapterPosition(), cliente);
                }
            });
        }

        void bindView(@NonNull Cliente cliente) {
            this.cliente = cliente;
            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
            imagemCliente.setImageDrawable(TextDrawable.builder()
                    .buildRound(cliente.getPrimeiraLetraNome(), colorGenerator.getRandomColor()));
            nomeCliente.setText(cliente.getNomeCompleto());
            telefoneCliente.setText(cliente.getTelefone());
            devendoCliente.setText(context.getString(R.string.sifra, cliente.getValorEmDeficit()));
            if (cliente.estaDevendo())
                devendoCliente.setTextColor(context.getResources().getColor(R.color.color_nao_pago));
            else devendoCliente.setTextColor(context.getResources().getColor(R.color.color_pago));
        }
    }
}
