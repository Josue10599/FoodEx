package com.fulltime.foodex.helper.update;

import androidx.annotation.NonNull;

import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

    public static void listaClientes() {
        firestoreAdapter.getCliente(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                assert queryDocumentSnapshots != null;
                if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                    eventBus.post(new ListaCliente(queryDocumentSnapshots.toObjects(Cliente.class)));
                }
            }
        });
    }

    public static void listaProdutos() {
        firestoreAdapter.getProdutos(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                assert queryDocumentSnapshots != null;
                if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                    eventBus.post(new ListaProduto(queryDocumentSnapshots.toObjects(Produto.class)));
                }
            }
        });
    }

    public static void listaVendas() {
        firestoreAdapter.getVendas(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                assert queryDocumentSnapshots != null;
                if (requisicaoNaoEstiverVazia(queryDocumentSnapshots))
                    eventBus.post(new ListaVenda(queryDocumentSnapshots.toObjects(Venda.class)));
            }
        });
    }

    public static void atualizaVenda(final Venda venda) {
        firestoreAdapter.setVenda(venda,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        eventBus.post(venda);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        eventBus.post(ERRO_ADICIONAR_VENDA);
                    }
                });
    }

    public static void atualizaProduto(final Produto produto) {
        firestoreAdapter.setProduto(produto,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        eventBus.post(produto);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        eventBus.post(ERRO_ADICIONAR_PRODUTO);
                    }
                });
    }

    public static void atualizaCliente(final Cliente cliente) {
        firestoreAdapter.setCliente(cliente,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        eventBus.post(cliente);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        eventBus.post(ERRO_ADICIONAR_CLIENTE);
                    }
                });
    }

    public static void removerCliente(Cliente cliente, final int posicao) {
        firestoreAdapter.removeCliente(cliente,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        eventBus.post(new RemoveCliente(posicao));
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        eventBus.post(ERRO_DELETAR_CLIENTE);
                    }
                });
    }

    public static void removeProduto(Produto produto, final int posicao) {
        firestoreAdapter.removeProduto(produto,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        eventBus.post(new RemoveProduto(posicao));
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        eventBus.post(ERRO_DELETAR_PRODUTO);
                    }
                });
    }

    private static boolean requisicaoNaoEstiverVazia(@Nullable QuerySnapshot queryDocumentSnapshots) {
        assert queryDocumentSnapshots != null;
        return !queryDocumentSnapshots.isEmpty();
    }
}
