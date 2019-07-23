package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.ui.recyclerview.adapter.ClienteAdapter;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;

import javax.annotation.Nullable;

import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_CLIENTE;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_PRODUTO;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_VENDA;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_DELETAR_CLIENTE;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_DELETAR_PRODUTO;

public class UpdateData {

    private final static FirestoreAdapter firestoreAdapter = FirestoreAdapter.build();
    private final static EventBus eventBus = EventBus.getDefault();

    public static void usuario(Usuario usuario) {
        firestoreAdapter.adicionaUsuario(usuario);
    }

    public static void listaClientes() {
        firestoreAdapter.getCliente((queryDocumentSnapshots, e) -> {
            assert queryDocumentSnapshots != null;
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                eventBus.post(new ListaCliente(queryDocumentSnapshots.toObjects(Cliente.class)));
            }
        });
    }

    public static void listaProdutos() {
        firestoreAdapter.getProdutos((queryDocumentSnapshots, e) -> {
            assert queryDocumentSnapshots != null;
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                eventBus.post(new ListaProduto(queryDocumentSnapshots.toObjects(Produto.class)));
            }
        });
    }

    public static void listaVendas() {
        firestoreAdapter.getVendas((queryDocumentSnapshots, e) -> {
            assert queryDocumentSnapshots != null;
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots))
                eventBus.post(new ListaVenda(queryDocumentSnapshots.toObjects(Venda.class)));
        });
    }

    public static void atualizaVenda(final Venda venda) {
        firestoreAdapter.setVenda(venda,
                aVoid -> eventBus.post(venda),
                e -> eventBus.post(ERRO_ADICIONAR_VENDA));
    }

    public static void atualizaProduto(final Produto produto) {
        firestoreAdapter.setProduto(produto,
                aVoid -> eventBus.post(produto),
                e -> eventBus.post(ERRO_ADICIONAR_PRODUTO));
    }

    public static void atualizaCliente(final Cliente cliente) {
        firestoreAdapter.setCliente(cliente,
                aVoid -> eventBus.post(cliente),
                e -> eventBus.post(ERRO_ADICIONAR_CLIENTE));
    }

    public static void removerCliente(Cliente cliente, ClienteAdapter adapter, final int posicao) {
        firestoreAdapter.removeCliente(cliente,
                aVoid -> adapter.removeCliente(posicao),
                e -> eventBus.post(ERRO_DELETAR_CLIENTE));
    }

    public static void removeProduto(Produto produto, ProdutoAdapter adapter, final int posicao) {
        firestoreAdapter.removeProduto(produto,
                aVoid -> adapter.removeProduto(posicao),
                e -> eventBus.post(ERRO_DELETAR_PRODUTO));
    }

    private static boolean requisicaoNaoEstiverVazia(@Nullable QuerySnapshot queryDocumentSnapshots) {
        return queryDocumentSnapshots != null;
    }
}
