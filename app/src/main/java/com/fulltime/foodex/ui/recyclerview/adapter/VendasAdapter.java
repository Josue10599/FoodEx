package com.fulltime.foodex.ui.recyclerview.adapter;

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

import java.util.ArrayList;
import java.util.List;

public class VendasAdapter extends RecyclerView.Adapter<VendasAdapter.VendasViewHolder> {

    private List<Venda> vendasRealizadas = new ArrayList<>();
    private Context context;

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

    public void adicionaVenda(Venda venda) {
        if (!vendasRealizadas.contains(venda)) {
            vendasRealizadas.add(venda);
            notifyItemInserted(vendasRealizadas.size() - 1);
        }
    }

    public void setLista(List<Venda> vendas) {
        vendasRealizadas = vendas;
        notifyItemRangeInserted(0, vendas.size());
    }

    class VendasViewHolder extends RecyclerView.ViewHolder {

        final TextView dataVenda;
        final TextView nomeClienteComprador;
        final TextView nomeProdutoVendido;
        final TextView descricaoProduto;
        final TextView valorDaVenda;

        VendasViewHolder(@NonNull View itemView) {
            super(itemView);
            dataVenda = itemView.findViewById(R.id.item_venda_data_venda);
            nomeClienteComprador = itemView.findViewById(R.id.item_venda_nome_cliente_comprador);
            nomeProdutoVendido = itemView.findViewById(R.id.item_venda_nome_produto_vendido);
            descricaoProduto = itemView.findViewById(R.id.item_venda_descricao_produto_vendido);
            valorDaVenda = itemView.findViewById(R.id.item_venda_valor_da_venda);
        }

        void bindViewHolder(Venda venda) {
            Cliente comprador = venda.getCliente();
            Produto produtoComprado = venda.getProdutoVendido();
            dataVenda.setText(venda.getDataVenda());
            nomeClienteComprador.setText(comprador.nomeCompleto());
            nomeProdutoVendido.setText(produtoComprado.getNome());
            descricaoProduto.setText(produtoComprado.getDescricao());
            valorDaVenda.setText(String.format(context.getString(R.string.sifra),
                    venda.getValorDaCompra()));
        }
    }
}
