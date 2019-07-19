package com.fulltime.foodex.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.ui.fragments.ListaClientesFragment;
import com.fulltime.foodex.ui.fragments.ListaProdutosFragment;
import com.fulltime.foodex.ui.fragments.ListaVendasFragment;
import com.fulltime.foodex.ui.fragments.PerfilUsuarioFragment;
import com.fulltime.foodex.ui.fragments.bottomsheet.MenuOpcoesFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class GerenciarActivity extends AppCompatActivity {

    private static int itemSelectedBottonNavigation = R.id.bottom_nav_clients_cliente;
    private BottomNavigationView bottomNavigationView;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configuraFloatingActionButton();
        configuraBottomNavigation();
        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos != null) {
            usuario = (Usuario) dadosRecebidos.getSerializableExtra("user");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        itemSelectedBottonNavigation = bottomNavigationView.getSelectedItemId();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErroListener(int erro) {
        switch (erro) {
            case ERRO_DELETAR_CLIENTE:
                showSnackbar(R.string.erro_delete_cliente);
                break;
            case ERRO_DELETAR_PRODUTO:
                showSnackbar(R.string.erro_delete_produto);
                break;
            case ERRO_ADICIONAR_CLIENTE:
                showSnackbar(R.string.erro_add_cliente);
                break;
            case ERRO_ADICIONAR_PAGAMENTO:
                showSnackbar(R.string.erro_add_pagamento);
                break;
            case ERRO_ADICIONAR_PRODUTO:
                showSnackbar(R.string.erro_add_produto);
                break;
            case ERRO_ADICIONAR_VENDA:
                showSnackbar(R.string.erro_add_venda);
                break;
            case ERRO_FALHA_CONEXAO:
                showSnackbar(R.string.erro_falha_conexao);
                break;
        }
    }

    private void showSnackbar(int erro) {
        Snackbar.make(findViewById(R.id.activity_main_coordinator_layout), erro, Snackbar.LENGTH_SHORT).show();
    }

    private void configuraFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(v ->
                new MenuOpcoesFragments().show(getSupportFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG));
    }

    private void configuraBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            setFragment(item.getItemId());
            return true;
        });
        setFragment(itemSelectedBottonNavigation);
    }

    private void setFragment(int id) {
        switch (id) {
            case R.id.bottom_nav_clients_cliente:
                populaFragment(new ListaClientesFragment());
                break;
            case R.id.bottom_nav_products_produto:
                populaFragment(new ListaProdutosFragment());
                break;
            case R.id.bottom_nav_sales_venda:
                populaFragment(new ListaVendasFragment());
                break;
            case R.id.bottom_nav_user_perfil:
                populaFragment(new PerfilUsuarioFragment(usuario));
                break;
        }
    }

    private void populaFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame_principal, fragment);
        fragmentTransaction.commit();
    }

}
