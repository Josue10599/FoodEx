package com.fulltime.foodex.firebase.firestore;

/*
  FoodEx is a sales management application.
  Copyright (C) 2019 Josue Lopes.
  <p>
  FoodEx is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  <p>
  FoodEx is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  <p>
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Empresa;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import static com.google.firebase.firestore.Query.Direction.DESCENDING;

public class FirestoreAdapter {
    private static final String CLIENTES = "clientes";
    private static final String CLIENTES_CAMPO_NOME = "nome";
    private static final String CLIENTES_CAMPO_SOBRENOME = "sobrenome";
    private static final String PRODUTOS = "produtos";
    private static final String VENDAS = "vendas";
    private static final String VENDAS_CAMPO_DATA_VENDA = "dataVenda";
    private static final String USUARIO = "usuario";
    private static final String EMPRESA = "empresa";

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

    public void getEmpresa(EventListener<QuerySnapshot> eventListener) {
        getUser().collection(EMPRESA).limit(1).addSnapshotListener(eventListener);
    }

    public void getAllCliente(EventListener<QuerySnapshot> eventListener) {
        getUser().collection(CLIENTES)
                .orderBy(CLIENTES_CAMPO_NOME, Query.Direction.ASCENDING)
                .orderBy(CLIENTES_CAMPO_SOBRENOME, Query.Direction.ASCENDING)
                .addSnapshotListener(eventListener);
    }

    public void getProdutos(EventListener<QuerySnapshot> eventListener) {
        getUser().collection(PRODUTOS).addSnapshotListener(eventListener);
    }

    public void getVendas(EventListener<QuerySnapshot> eventListener) {
        getUser().collection(VENDAS)
                .orderBy(VENDAS_CAMPO_DATA_VENDA, DESCENDING)
                .addSnapshotListener(eventListener);
    }

    public void setCliente(Cliente cliente,
                           OnFailureListener onFailureListener) {
        getDocument(CLIENTES, cliente.getId())
                .set(cliente)
                .addOnFailureListener(onFailureListener);
    }

    public void setProduto(Produto produto,
                           OnFailureListener onFailureListener) {
        getDocument(PRODUTOS, produto.getId()).set(produto)
                .addOnFailureListener(onFailureListener);
    }

    public void setVenda(Venda venda,
                         OnFailureListener onFailureListener) {
        getDocument(VENDAS, venda.getId()).set(venda)
                .addOnFailureListener(onFailureListener);
    }

    public void setEmpresa(Empresa empresa,
                           OnSuccessListener<Void> onSuccessListener,
                           OnFailureListener onFailureListener) {
        getDocument(EMPRESA, empresa.getId()).set(empresa)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void removeCliente(Cliente cliente,
                              OnFailureListener onFailureListener) {
        getDocument(CLIENTES, cliente.getId())
                .delete()
                .addOnFailureListener(onFailureListener);
    }

    public void removeProduto(Produto produto,
                              OnFailureListener onFailureListener) {
        getDocument(PRODUTOS, produto.getId())
                .delete()
                .addOnFailureListener(onFailureListener);
    }

    public void adicionaUsuario(Usuario usuario) {
        getUser().set(usuario);
    }

    private DocumentReference getDocument(String collection, String id) {
        return getUser().collection(collection).document(id);
    }

    private DocumentReference getUser() {
        String uidUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        return db.collection(USUARIO).document(uidUser);
    }
}
