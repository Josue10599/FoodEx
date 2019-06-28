package com.fulltime.foodex.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fulltime.foodex.R;
import com.fulltime.foodex.ui.fragments.ClientesFragment;
import com.fulltime.foodex.ui.fragments.PerfilFragment;
import com.fulltime.foodex.ui.fragments.ProdutosFragment;
import com.fulltime.foodex.ui.fragments.VendasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populaFragment(new ClientesFragment());
        configuraBottomNavigation();
    }

    private void configuraBottomNavigation() {
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
                return true;
            }
        });
    }

    private void populaFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame_principal, fragment);
        fragmentTransaction.commit();
    }

}
