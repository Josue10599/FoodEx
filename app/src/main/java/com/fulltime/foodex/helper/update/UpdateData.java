package com.fulltime.foodex.helper.update;

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
import com.fulltime.foodex.firebase.firestore.FirestoreAdapter;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Empresa;
import com.fulltime.foodex.model.Pagamento;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.annotation.Nullable;

import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_CLIENTE;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_PAGAMENTO;
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

    public static void setEmpresa(Empresa empresa) {
        firestoreAdapter.setEmpresa(empresa,
                aVoid -> {
                    eventBus.post(empresa);
                    eventBus.post(new ChangeEmpresa(empresa));
                }, e -> eventBus.post(ERRO_FALHA_CONEXAO));
    }

    public static void getEmpresa() {
        firestoreAdapter.getEmpresa((queryDocumentSnapshots, e) -> {
            if (e != null) {
                eventBus.post(ERRO_FALHA_CONEXAO);
                return;
            }
            if (requisicaoNaoEstiverVazia(queryDocumentSnapshots)) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                if (!documents.isEmpty()) {
                    Empresa empresa = documents.get(0).toObject(Empresa.class);
                    eventBus.post(empresa);
                } else eventBus.post(new FirstCorporation(new Empresa()));
            }
        });
    }

    public static void listaTodosClientes() {
        firestoreAdapter.getAllCliente((querySnapshot, e) -> {
            if (e != null) {
                eventBus.post(ERRO_FALHA_CONEXAO);
                return;
            }
            if (requisicaoNaoEstiverVazia(querySnapshot)) {
                if (!querySnapshot.isEmpty())
                    eventBus.post(new ListaCliente(querySnapshot.toObjects(Cliente.class)));
                else eventBus.post(new ListaClienteVazia());
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
                if (!querySnapshot.isEmpty())
                    eventBus.post(new ListaProduto(querySnapshot.toObjects(Produto.class)));
                else eventBus.post(new ListaProdutoVazia());
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
                if (!querySnapshot.isEmpty())
                    eventBus.post(new ListaVenda(querySnapshot.toObjects(Venda.class)));
                else eventBus.post(new ListaVendaVazia());
            }
        });
    }

    public static void listaPagamentos() {
        firestoreAdapter.getPagamentos((querySnapshot, e) -> {
            if (e != null) {
                eventBus.post(ERRO_FALHA_CONEXAO);
                return;
            }
            if (requisicaoNaoEstiverVazia(querySnapshot)) {
                if (!querySnapshot.isEmpty())
                    eventBus.post(new ListaPagamentos(querySnapshot.toObjects(Pagamento.class)));
                else eventBus.post(new ListaPagamentoVazia());
            }
        });
    }

    public static void atualizaVenda(final Venda venda) {
        firestoreAdapter.setVenda(venda, e -> eventBus.post(ERRO_ADICIONAR_VENDA));
        eventBus.post(venda);
    }

    public static void atualizaPagamento(Pagamento pagamento) {
        firestoreAdapter.setPagamento(pagamento, e -> eventBus.post(ERRO_ADICIONAR_PAGAMENTO));
        eventBus.post(pagamento);
    }

    public static void atualizaProduto(final Produto produto) {
        firestoreAdapter.setProduto(produto, e -> eventBus.post(ERRO_ADICIONAR_PRODUTO));
        eventBus.post(produto);
    }

    public static void atualizaCliente(final Cliente cliente) {
        firestoreAdapter.setCliente(cliente, e -> eventBus.post(ERRO_ADICIONAR_CLIENTE));
        eventBus.post(cliente);
    }

    public static void removerCliente(RemoveCliente removeCliente) {
        removeCliente.getAdapter().removeCliente(removeCliente.getPosicao());
        firestoreAdapter.removeCliente(removeCliente.getCliente(), e -> eventBus.post(ERRO_DELETAR_CLIENTE));
    }

    public static void removeProduto(RemoveProduto removeProduto) {
        removeProduto.getAdapter().removeProduto(removeProduto.getPosicao());
        firestoreAdapter.removeProduto(removeProduto.getProduto(), e -> eventBus.post(ERRO_DELETAR_PRODUTO));
    }

    private static boolean requisicaoNaoEstiverVazia(@Nullable QuerySnapshot queryDocumentSnapshots) {
        return queryDocumentSnapshots != null;
    }
}
