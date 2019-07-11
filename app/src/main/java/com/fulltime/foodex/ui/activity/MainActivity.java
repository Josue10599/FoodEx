package com.fulltime.foodex.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fulltime.foodex.R;
import com.fulltime.foodex.ui.fragments.ClientesFragment;
import com.fulltime.foodex.ui.fragments.PerfilFragment;
import com.fulltime.foodex.ui.fragments.ProdutosFragment;
import com.fulltime.foodex.ui.fragments.VendasFragment;
import com.fulltime.foodex.ui.fragments.bottomsheet.MenuOpcoesFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.fulltime.foodex.ui.fragments.bottomsheet.Constantes.BOTTOM_SHEET_FRAGMENT_TAG;
import static com.google.android.material.bottomnavigation.BottomNavigationView.OnClickListener;
import static com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    private static int itemSelectedBottonNavigation = R.id.bottom_nav_clients_cliente;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configuraFloatingActionButton();
        configuraBottomNavigation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        itemSelectedBottonNavigation = bottomNavigationView.getSelectedItemId();
    }

    private void configuraFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new MenuOpcoesFragments().show(getSupportFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
            }
        });
    }

    private void configuraBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setFragment(item.getItemId());
                return true;
            }
        });
        setFragment(itemSelectedBottonNavigation);
    }

    private void setFragment(@NonNull int id) {
        switch (id) {
            case R.id.bottom_nav_clients_cliente:
                populaFragment(new ClientesFragment());
                break;
            case R.id.bottom_nav_products_produto:
                populaFragment(new ProdutosFragment());
                break;
            case R.id.bottom_nav_sales_venda:
                populaFragment(new VendasFragment());
                break;
            case R.id.bottom_nav_user_perfil:
                populaFragment(new PerfilFragment());
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
