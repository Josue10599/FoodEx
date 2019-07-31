package com.fulltime.foodex.ui.activity;

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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.helper.update.RemoveCliente;
import com.fulltime.foodex.helper.update.RemoveProduto;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Produto;
import com.fulltime.foodex.model.Venda;
import com.fulltime.foodex.ui.fragments.ListaClientesFragment;
import com.fulltime.foodex.ui.fragments.ListaProdutosFragment;
import com.fulltime.foodex.ui.fragments.ListaVendasFragment;
import com.fulltime.foodex.ui.fragments.PerfilUsuarioFragment;
import com.fulltime.foodex.ui.fragments.bottomsheet.MenuOpcoesFragments;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_CLIENTE;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_PAGAMENTO;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_PRODUTO;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_ADICIONAR_VENDA;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_DELETAR_CLIENTE;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_DELETAR_PRODUTO;
import static com.fulltime.foodex.error.CodigoDeErro.ERRO_FALHA_CONEXAO;
import static com.fulltime.foodex.firebase.authentication.Usuario.USER;
import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class GerenciarActivity extends AppCompatActivity {

    private static int itemSelectedBottonNavigation = R.id.bottom_nav_clients_cliente;
    private BottomNavigationView bottomNavigationView;
    private Usuario usuario;
    private View coordinatorLayoutForSnackBar;
    private BadgeDrawable badgeCliente;
    private BadgeDrawable badgeProdutos;
    private BadgeDrawable badgeVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos != null) {
            usuario = (Usuario) dadosRecebidos.getSerializableExtra(USER);
            UpdateData.usuario(usuario);
        }
        coordinatorLayoutForSnackBar = findViewById(R.id.activity_main_coordinator_layout);
        configuraFloatingActionButton();
        configuraBottomNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        itemSelectedBottonNavigation = bottomNavigationView.getSelectedItemId();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErroListener(int erro) {
        switch (erro) {
            case ERRO_DELETAR_CLIENTE:
                showSnackbar(R.string.error_delete_client);
                break;
            case ERRO_DELETAR_PRODUTO:
                showSnackbar(R.string.error_delete_product);
                break;
            case ERRO_ADICIONAR_CLIENTE:
                showSnackbar(R.string.error_add_client);
                break;
            case ERRO_ADICIONAR_PAGAMENTO:
                showSnackbar(R.string.error_add_pay);
                break;
            case ERRO_ADICIONAR_PRODUTO:
                showSnackbar(R.string.error_add_product);
                break;
            case ERRO_ADICIONAR_VENDA:
                showSnackbar(R.string.error_add_sale);
                break;
            case ERRO_FALHA_CONEXAO:
                showSnackbar(R.string.error_connect);
                break;
        }
    }

    @Subscribe
    public void onDeleteProduto(RemoveProduto removeProduto) {
        String dadosProduto = removeProduto.getProduto().getNome() + " (" + removeProduto.getProduto().getDescricao() + ")";
        String textoFormatado = String.format(getString(R.string.deseja_apagar), dadosProduto);
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.delete_product)
                .setMessage(textoFormatado)
                .setPositiveButton(R.string.delete, (dialogInterface, i) ->
                        UpdateData.removeProduto(removeProduto.getProduto(),
                                removeProduto.getAdapter(),
                                removeProduto.getPosicao()))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Subscribe
    public void onDeleteCliente(RemoveCliente removeCliente) {
        String textoFormatado = String.format(getString(R.string.deseja_apagar),
                removeCliente.getCliente().nomeCompleto());
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.delete_client)
                .setMessage(textoFormatado)
                .setPositiveButton(R.string.delete, (dialogInterface, i) ->
                        UpdateData.removerCliente(removeCliente.getCliente(),
                                removeCliente.getAdapter(),
                                removeCliente.getPosicao()))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Subscribe
    public void onCreateCliente(Cliente cliente) {
        setBadge(R.id.bottom_nav_clients_cliente, badgeCliente);
    }

    @Subscribe
    public void onCreateProduto(Produto produto) {
        setBadge(R.id.bottom_nav_products_produto, badgeProdutos);
    }

    @Subscribe
    public void onCreateVenda(Venda venda) {
        setBadge(R.id.bottom_nav_sales_venda, badgeVendas);
    }

    private void setBadge(int p, BadgeDrawable badgeCliente) {
        if (bottomNavigationView.getSelectedItemId() != p) {
            badgeCliente.setVisible(true);
            badgeCliente.setNumber(badgeCliente.getNumber() + 1);
        }
    }

    private void showSnackbar(int erro) {
        Snackbar.make(coordinatorLayoutForSnackBar, erro, Snackbar.LENGTH_SHORT).show();
    }

    private void configuraFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(v ->
                new MenuOpcoesFragments().show(getSupportFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG));
    }

    private void configuraBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        configuraBadge();
        bottomNavigationView.setSelectedItemId(itemSelectedBottonNavigation);
        setFragment(itemSelectedBottonNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            setFragment(item.getItemId());
            return true;
        });
    }

    private void setFragment(int id) {
        switch (id) {
            case R.id.bottom_nav_clients_cliente:
                populaFragment(new ListaClientesFragment());
                clearBadge(badgeCliente);
                break;
            case R.id.bottom_nav_products_produto:
                populaFragment(new ListaProdutosFragment());
                clearBadge(badgeProdutos);
                break;
            case R.id.bottom_nav_sales_venda:
                populaFragment(new ListaVendasFragment());
                clearBadge(badgeVendas);
                break;
            case R.id.bottom_nav_user_perfil:
                populaFragment(new PerfilUsuarioFragment(usuario));
                break;
        }
    }

    private void configuraBadge() {
        badgeCliente = bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_clients_cliente);
        badgeProdutos = bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_products_produto);
        badgeVendas = bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_sales_venda);
        clearBadge(badgeCliente);
        clearBadge(badgeProdutos);
        clearBadge(badgeVendas);
    }

    private void clearBadge(BadgeDrawable badgeCliente) {
        badgeCliente.clearNumber();
        badgeCliente.setVisible(false);
    }

    private void populaFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame_principal, fragment);
        fragmentTransaction.commit();
    }

}
