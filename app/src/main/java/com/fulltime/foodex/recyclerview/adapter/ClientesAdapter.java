package com.fulltime.foodex.recyclerview.adapter;

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
import com.fulltime.foodex.recyclerview.adapter.listener.OnItemClickListener;

import java.util.Collections;
import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder>{
    private final List<Cliente> listaClientes;
    private OnItemClickListener onItemClickListener;

    public ClientesAdapter(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        listaClientes.add(0, novoCliente);
        notifyItemInserted(0);
    }

    public void alteraCliente(int posicao, Cliente clienteAlterado) {
        listaClientes.set(posicao, clienteAlterado);
        notifyItemChanged(posicao);
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
        private ImageView imagemCliente;
        private TextView nomeCliente;
        private TextView telefoneCliente;
        private TextView devendoCliente;

        public ClienteViewHolder(@NonNull final View itemView) {
            super(itemView);
            findView(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(getAdapterPosition(), v);
                }
            });
        }

        private void findView(@NonNull View itemView) {
            imagemCliente = itemView.findViewById(R.id.item_cliente_foto);
            nomeCliente = itemView.findViewById(R.id.item_cliente_nome);
            telefoneCliente = itemView.findViewById(R.id.item_cliente_telefone);
            devendoCliente = itemView.findViewById(R.id.item_cliente_valor);
        }

        public void bindView(@NonNull Cliente cliente) {
            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
            imagemCliente.setImageDrawable(TextDrawable.builder()
                    .buildRect(cliente.getPrimeiraLetraNome(), colorGenerator.getRandomColor()));
            nomeCliente.setText(cliente.getNome());
            telefoneCliente.setText(cliente.getTelefone());
            devendoCliente.setText(cliente.getValor());
        }
    }
}
