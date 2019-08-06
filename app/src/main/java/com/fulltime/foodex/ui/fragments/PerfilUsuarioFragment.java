package com.fulltime.foodex.ui.fragments;

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

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.helper.eventbus.FirstCorporation;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Empresa;
import com.fulltime.foodex.ui.fragments.dialog.EditaEmpresaFragment;
import com.fulltime.foodex.ui.image.ImageLoader;
import com.fulltime.foodex.ui.scroll.ScrollChangeListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.fulltime.foodex.R.id;
import static com.fulltime.foodex.R.layout;

public class PerfilUsuarioFragment extends Fragment {

    private static final String DIALOG = "DIALOG";
    private final Usuario usuario;
    private View perfilUsuarioFragment;
    private ProgressBar loading;
    private MaterialButton buttonAddBusiness;
    private Empresa empresa = new Empresa();

    public PerfilUsuarioFragment(Usuario usuario) {
        this.usuario = usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        perfilUsuarioFragment = inflater.inflate(layout.fragment_perfil, container, false);
        configuraLoading();
        configuraFotoPerfil();
        configuraNomeUsuario();
        buttonBusinessConfig();
        scrollViewConfig();
        UpdateData.getEmpresa();
        return perfilUsuarioFragment;
    }

    private void scrollViewConfig() {
        ScrollView scrollView = perfilUsuarioFragment.findViewById(id.fragment_perfil_scroll);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            scrollView.setOnScrollChangeListener(new ScrollChangeListener());
    }

    private void configuraLoading() {
        loading = perfilUsuarioFragment.findViewById(id.fragment_perfil_loading);
        loading.setActivated(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getDadosEmpresa(Empresa empresa) {
        this.empresa = empresa;
        configuraDadosDaEmpresa();
        setGone(loading);
        setGone(buttonAddBusiness);
    }

    @Subscribe
    public void firstCorporation(FirstCorporation firstCorporation) {
        setGone(loading);
        setVisible(buttonAddBusiness);
        empresa = firstCorporation.getEmpresa();
        buttonAddBusiness.setOnClickListener(view -> {
            openDialogConfig();
            setGone(buttonAddBusiness);
        });
    }

    private void buttonBusinessConfig() {
        ImageButton setUpBusinessButton = perfilUsuarioFragment.findViewById(id.fragment_perfil_config_button);
        buttonAddBusiness = perfilUsuarioFragment.findViewById(id.fragment_perfil_button_add_business);
        setUpBusinessButton.setOnClickListener(view -> openDialogConfig());
    }

    private void openDialogConfig() {
        assert getFragmentManager() != null;
        new EditaEmpresaFragment().show(getFragmentManager(), DIALOG);
    }

    private void configuraNomeUsuario() {
        MaterialTextView textViewNomeUsuario = perfilUsuarioFragment
                .findViewById(id.fragment_perfil_nome_usuario);
        textViewNomeUsuario.setText(usuario.getDisplayName());
    }

    private void configuraFotoPerfil() {
        ImageView fotoPerfil = perfilUsuarioFragment.findViewById(id.fragment_perfil_foto_usuario);
        new ImageLoader(getContext()).getPhotoUsuario(usuario, fotoPerfil);
    }

    private void configuraDadosDaEmpresa() {
        MaterialTextView textViewNome = perfilUsuarioFragment.findViewById(id.fragment_perfil_nome_empresa);
        MaterialTextView textViewTelefone = perfilUsuarioFragment.findViewById(id.fragment_perfil_telefone_empresa);
        MaterialTextView textViewEmail = perfilUsuarioFragment.findViewById(id.fragment_perfil_email_empresa);
        MaterialTextView textViewEndereco = perfilUsuarioFragment.findViewById(id.fragment_perfil_endereco_empresa);
        MaterialTextView textViewEmpresaNome = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_nome);
        MaterialTextView textViewEmpresaTelefone = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_telefone);
        MaterialTextView textViewEmpresaEmail = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_email);
        MaterialTextView textViewEmpresaEndereco = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_endereco);
        setVisible(textViewNome);
        setVisible(textViewTelefone);
        setVisible(textViewEmail);
        setVisible(textViewEndereco);
        setVisible(textViewEmpresaNome);
        setVisible(textViewEmpresaTelefone);
        setVisible(textViewEmpresaEmail);
        setVisible(textViewEmpresaEndereco);
        setDataEmpresa(textViewEmpresaNome, textViewEmpresaTelefone, textViewEmpresaEmail, textViewEmpresaEndereco);
    }

    private void setDataEmpresa(MaterialTextView textViewEmpresaNome, MaterialTextView textViewEmpresaTelefone, MaterialTextView textViewEmpresaEmail, MaterialTextView textViewEmpresaEndereco) {
        textViewEmpresaNome.setText(empresa.getNomeEmpresa());
        textViewEmpresaTelefone.setText(empresa.getTelefoneEmpresa());
        textViewEmpresaEmail.setText(empresa.getEmailEmpresa());
        textViewEmpresaEndereco.setText(empresa.getEnderecoEmpresa());
    }

    private void setVisible(View view) {
        view.setVisibility(VISIBLE);
    }

    private void setGone(View view) {
        view.setVisibility(GONE);
    }
}
