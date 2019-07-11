package com.fulltime.foodex.firebase.firestore;

import android.util.Log;

import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class FirestoreAdapter {

    private static final String CLIENTES = "clientes";
    private static final String TAG_FIRESTONE = "Firestone";
    private static final String PRODUTOS = "produtos";
    private static final String VENDAS = "vendas";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirestoreAdapter() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().
                setPersistenceEnabled(true).
                build();
        db.setFirestoreSettings(settings);
    }

    public static FirestoreAdapter build() {
        return new FirestoreAdapter();
    }

    public void getClientes(final OnQueryListener onQueryListener) {
        final List<Cliente> clientes = new ArrayList<>();
        db.collection(CLIENTES).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                populaLista(queryDocumentSnapshots, clientes, Cliente.class, onQueryListener);
            }
        });
    }

    public void getProdutos(final OnQueryListener onQueryListener) {
        final List<Produto> produtos = new ArrayList<>();
        db.collection(PRODUTOS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                populaLista(queryDocumentSnapshots, produtos, Produto.class, onQueryListener);
            }
        });
    }

    public void getVendas(final OnQueryListener onQueryListener) {
        final List<Venda> vendas = new ArrayList<>();
        db.collection(VENDAS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                populaLista(queryDocumentSnapshots, vendas, Venda.class, onQueryListener);
            }
        });
    }

    public void setCliente(Cliente cliente) {
        db.collection(CLIENTES).document(cliente.getId()).set(cliente);
    }

    public void setProduto(Produto produto) {
        db.collection(PRODUTOS).document(produto.getId()).set(produto);
    }

    public void setVendas(Venda venda) {
        db.collection(VENDAS).document(venda.getId()).set(venda);
    }

    private void populaLista(QuerySnapshot documentSnapshots, List list, Class _class, OnQueryListener onQueryListener) {
        if (documentSnapshots != null) {
            for (DocumentChange document : documentSnapshots.getDocumentChanges()) {
                list.add(document.getDocument().toObject(_class));
                Log.d(TAG_FIRESTONE, document.getDocument().getId() + " => " + document.getDocument().getData());
            }
            onQueryListener.onSucessful(list);
        }
    }
}
