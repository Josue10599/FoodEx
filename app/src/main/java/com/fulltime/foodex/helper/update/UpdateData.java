package com.fulltime.foodex.helper.update;

import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Empresa;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.ui.recyclerview.adapter.ClienteAdapter;
import com.fulltime.foodex.ui.recyclerview.adapter.ProdutoAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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

    public static void setEmpresa(Empresa empresa, OnSuccessListener<Empresa> onSuccessListener) {
        firestoreAdapter.setEmpresa(empresa,
                aVoid -> {
                    eventBus.post(empresa);
                    onSuccessListener.onSuccess(empresa);
                }, e -> eventBus.post(ERRO_FALHA_CONEXAO));
    }

    public static void setEmpresa(Empresa empresa) {
        firestoreAdapter.setEmpresa(empresa,
                aVoid -> eventBus.post(empresa),
                e -> eventBus.post(ERRO_FALHA_CONEXAO));
    }

    public static void getEmpresa() {
        firestoreAdapter.getEmpresa((queryDocumentSnapshots, e) -> {
            if (e != null) {
                eventBus.post(ERRO_FALHA_CONEXAO);
                return;
            }
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots))
                for (Empresa empresa : queryDocumentSnapshots.toObjects(Empresa.class))
                    eventBus.post(empresa);
        });
    }

    public static void listaTodosClientes() {
        firestoreAdapter.getAllCliente((querySnapshot, e) -> {
            if (e != null) {
                eventBus.post(ERRO_FALHA_CONEXAO);
                return;
            }
            if (requisicaoNaoEstiverVazia(querySnapshot)) {
                List<Cliente> list = new ArrayList<>();
                for (DocumentSnapshot doc : querySnapshot) {
                    if (doc.exists()) {
                        Cliente cliente = doc.toObject(Cliente.class);
                        list.add(cliente);
                    }
                }
                ListaCliente listaCliente = new ListaCliente(list);
                eventBus.post(listaCliente);
            }
        });
    }

    public static void listaProdutos() {
        firestoreAdapter.getProdutos((querySnapshot, e) -> {
            if (e != null) {
                eventBus.post(ERRO_FALHA_CONEXAO);
                return;
            }
            if (requisicaoNaoEstiverVazia(querySnapshot)) {
                List<Produto> list = new ArrayList<>();
                for (DocumentSnapshot doc : querySnapshot) {
                    if (doc.exists()) {
                        Produto produto = doc.toObject(Produto.class);
                        list.add(produto);
                    }
                }
                ListaProduto listaProduto = new ListaProduto(list);
                eventBus.post(listaProduto);
            }
        });
    }

    public static void listaVendas() {
        firestoreAdapter.getVendas((querySnapshot, e) -> {
            if (e != null) {
                eventBus.post(ERRO_FALHA_CONEXAO);
                return;
            }
            if (requisicaoNaoEstiverVazia(querySnapshot)) {
                List<Venda> vendas = new ArrayList<>();
                for (QueryDocumentSnapshot doc : querySnapshot) {
                    if (doc.exists()) {
                        Venda venda = doc.toObject(Venda.class);
                        vendas.add(venda);
                    }
                }
                ListaVenda listaVenda = new ListaVenda(vendas);
                eventBus.post(listaVenda);
            }
        });
    }

    public static void atualizaVenda(final Venda venda) {
        firestoreAdapter.setVenda(venda,
                e -> eventBus.post(ERRO_ADICIONAR_VENDA));
        eventBus.post(venda);
    }

    public static void atualizaProduto(final Produto produto) {
        firestoreAdapter.setProduto(produto,
                e -> eventBus.post(ERRO_ADICIONAR_PRODUTO));
        eventBus.post(produto);
    }

    public static void atualizaCliente(final Cliente cliente) {
        firestoreAdapter.setCliente(cliente, e -> eventBus.post(ERRO_ADICIONAR_CLIENTE));
        eventBus.post(cliente);
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

    private static boolean requisicaoNaoEstiverVazia(@Nullable QuerySnapshot
                                                             queryDocumentSnapshots) {
        return queryDocumentSnapshots != null;
    }
}
