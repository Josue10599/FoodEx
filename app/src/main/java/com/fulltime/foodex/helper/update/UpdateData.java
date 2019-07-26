package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Empresa;
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
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_FALHA_CONEXAO;

public class UpdateData {

    private final static FirestoreAdapter firestoreAdapter = FirestoreAdapter.build();
    private final static EventBus eventBus = EventBus.getDefault();

    public static void usuario(Usuario usuario) {
        firestoreAdapter.adicionaUsuario(usuario);
    }

    public static void getEmpresa() {
        firestoreAdapter.getEmpresa(documentSnapshot -> {
            Empresa empresa = documentSnapshot.toObject(Empresa.class);
            eventBus.post(empresa);
        }, e -> eventBus.post(ERRO_FALHA_CONEXAO));
    }

    public static void listaClientes() {
        firestoreAdapter.getCliente((queryDocumentSnapshots, e) -> {
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                ListaCliente listaCliente;
                listaCliente = new ListaCliente(queryDocumentSnapshots.toObjects(Cliente.class));
                eventBus.post(listaCliente);
            } else eventBus.post(ERRO_FALHA_CONEXAO);
        });
    }

    public static void listaProdutos() {
        firestoreAdapter.getProdutos((queryDocumentSnapshots, e) -> {
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                ListaProduto listaProduto;
                listaProduto = new ListaProduto(queryDocumentSnapshots.toObjects(Produto.class));
                eventBus.post(listaProduto);
            } else eventBus.post(ERRO_FALHA_CONEXAO);
        });
    }

    public static void listaVendas() {
        firestoreAdapter.getVendas((queryDocumentSnapshots, e) -> {
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                ListaVenda listaVenda;
                listaVenda = new ListaVenda(queryDocumentSnapshots.toObjects(Venda.class));
                eventBus.post(listaVenda);
            } else eventBus.post(ERRO_FALHA_CONEXAO);
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
