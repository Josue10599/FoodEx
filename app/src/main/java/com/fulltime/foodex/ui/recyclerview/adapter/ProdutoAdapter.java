package com.fulltime.foodex.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.firebase.firestore.OnQueryListener;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.ui.recyclerview.adapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProdutoAdapter extends Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private final List<Produto> produtosSalvos = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    public ProdutoAdapter() {
        FirestoreAdapter.build().getProdutos(new OnQueryListener() {
            @Override
            public void onSucessful(Object produto) {
                insereItem((Produto) produto);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ProdutoViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_produto, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        holder.bindHolder(this.produtosSalvos.get(position));
    }


    @Override
    public int getItemCount() {
        return this.produtosSalvos.size();
    }

    public void insereItem(Produto novoProduto) {
        if (!this.produtosSalvos.contains(novoProduto)) {
            this.produtosSalvos.add(novoProduto);
            notifyItemInserted(this.produtosSalvos.size() - 1);
        }
    }

    public void alteraItem(Produto produtoAlterado, int posicao) {
        this.produtosSalvos.set(posicao, produtoAlterado);
        notifyItemChanged(posicao);
    }

    public void removeItem(int posicao) {
        this.produtosSalvos.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void movimentaItem(int posicaoInicial, int posicaoFinal) {
        Collections.swap(produtosSalvos, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    class ProdutoViewHolder extends ViewHolder {
        private final TextView nomeProduto;
        private final TextView descricaoProduto;
        private final TextView valorProduto;
        private Produto produto;

        ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.item_produto_nome_do_produto);
            descricaoProduto = itemView.findViewById(R.id.item_produto_descricao_produto);
            valorProduto = itemView.findViewById(R.id.item_produto_valor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(getAdapterPosition(), produto);
                }
            });
        }

        void bindHolder(Produto produto) {
            this.produto = produto;
            nomeProduto.setText(produto.getNome());
            descricaoProduto.setText(produto.getDescricao());
            valorProduto.setText(context.getString(R.string.sifra, produto.getValor()));
        }

    }
}
