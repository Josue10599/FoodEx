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
import com.fulltime.foodex.model.Pagamento;
import com.fulltime.foodex.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PagamentosAdapter extends RecyclerView.Adapter<PagamentosAdapter.PagamentosViewHolder> {

    private List<Pagamento> pagamentosRealizados = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public PagamentosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new PagamentosViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pagamento, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PagamentosViewHolder holder, int position) {
        holder.bindViewHolder(pagamentosRealizados.get(position));
    }

    @Override
    public int getItemCount() {
        return pagamentosRealizados.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void adicionaPagamento(Pagamento pagamento) {
        if (!pagamentosRealizados.contains(pagamento)) {
            pagamentosRealizados.add(pagamento);
            notifyItemInserted(0);
        }
    }

    public void setLista(List<Pagamento> pagamentos) {
        pagamentosRealizados = pagamentos;
        notifyDataSetChanged();
    }

    class PagamentosViewHolder extends RecyclerView.ViewHolder {

        final TextView dataVenda;
        final TextView nomeCliente;
        final TextView valorPagamento;
        Pagamento pagamento;

        PagamentosViewHolder(@NonNull View itemView) {
            super(itemView);
            dataVenda = itemView.findViewById(R.id.item_pagamento_data_pagamento);
            nomeCliente = itemView.findViewById(R.id.item_pagamento_nome_cliente_comprador);
            valorPagamento = itemView.findViewById(R.id.item_pagamento_valor_do_pagamento);
            if (onItemClickListener != null)
                itemView.setOnClickListener(v -> onItemClickListener.onItemClickListener(getAdapterPosition(), pagamento));
        }

        void bindViewHolder(Pagamento pagamento) {
            this.pagamento = pagamento;
            Cliente comprador = pagamento.getCliente();
            dataVenda.setText(pagamento.dataPagamentoFormatada());
            nomeCliente.setText(comprador.nomeCompleto());
            valorPagamento.setText(context.getString(R.string.sifra, pagamento.valorDoPagamentoFormatado()));
        }
    }
}
