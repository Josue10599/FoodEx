package com.fulltime.foodex.firebase.firestore;

import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreAdapter {
    private static final String CLIENTES = "clientes";
    private static final String PRODUTOS = "produtos";
    private static final String VENDAS = "vendas";
    private static final String VENDAS_CAMPO_DATA_VENDA = "dataVenda";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirestoreAdapter() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().
                setPersistenceEnabled(true).
                build();
        db.setFirestoreSettings(settings);
    }

    public static FirestoreAdapter build() {
        return new FirestoreAdapter();
    }

    public void getCliente(EventListener<QuerySnapshot> eventListener) {
        db.collection(CLIENTES).addSnapshotListener(eventListener);
    }

    public void getProdutos(EventListener<QuerySnapshot> eventListener) {
        db.collection(PRODUTOS).addSnapshotListener(eventListener);
    }

    public void getVendas(EventListener<QuerySnapshot> eventListener) {
        db.collection(VENDAS).orderBy(VENDAS_CAMPO_DATA_VENDA, Query.Direction.DESCENDING)
                .addSnapshotListener(eventListener);
    }

    public void setCliente(Cliente cliente,
                           OnSuccessListener<Void> onSuccessListener,
                           OnFailureListener onFailureListener) {
        getDocument(CLIENTES, cliente.getId())
                .set(cliente)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void setProduto(Produto produto,
                           OnSuccessListener<Void> onSuccessListener,
                           OnFailureListener onFailureListener) {
        getDocument(PRODUTOS, produto.getId()).set(produto)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void setVenda(Venda venda,
                         OnSuccessListener<Void> onSuccessListener,
                         OnFailureListener onFailureListener) {
        getDocument(VENDAS, venda.getId()).set(venda)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void removeCliente(Cliente cliente,
                              OnSuccessListener<Void> onSuccessListener,
                              OnFailureListener onFailureListener) {
        getDocument(CLIENTES, cliente.getId())
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void removeProduto(Produto produto,
                              OnSuccessListener<Void> onSuccessListener,
                              OnFailureListener onFailureListener) {
        getDocument(PRODUTOS, produto.getId())
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    private DocumentReference getDocument(String collection, String id) {
        return db.collection(collection).document(id);
    }
}
