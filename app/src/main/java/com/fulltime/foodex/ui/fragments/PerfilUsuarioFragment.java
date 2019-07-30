package com.fulltime.foodex.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.helper.update.FirstCorporation;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Empresa;
import com.fulltime.foodex.ui.fragments.dialog.EditaEmpresaFragment;
import com.fulltime.foodex.ui.image.ImageLoader;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.fulltime.foodex.R.id;
import static com.fulltime.foodex.R.layout;

public class PerfilUsuarioFragment extends Fragment {

    private final Usuario usuario;
    private View perfilUsuarioFragment;
    private ProgressBar loading;
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
        UpdateData.getEmpresa();
        return perfilUsuarioFragment;
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
    }

    @Subscribe
    public void firstCorporation(FirstCorporation firstCorporation) {
        setGone(loading);
        empresa = firstCorporation.getEmpresa();
        MaterialButton buttonAddBusiness = perfilUsuarioFragment.findViewById(id.fragment_perfil_button_add_business);
        setVisible(buttonAddBusiness);
        buttonAddBusiness.setOnClickListener(view -> {
            openDialogConfig();
            setGone(buttonAddBusiness);
        });
    }

    private void buttonBusinessConfig() {
        ImageButton setUpBusinessButton = perfilUsuarioFragment.findViewById(id.fragment_perfil_config_button);
        setUpBusinessButton.setOnClickListener(view -> openDialogConfig());
    }

    private void openDialogConfig() {
        EditaEmpresaFragment editaEmpresaFragment = new EditaEmpresaFragment(empresa);
        editaEmpresaFragment.show(getFragmentManager(), "DIALOG");
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

    private void setVisible(View textViewNome) {
        textViewNome.setVisibility(VISIBLE);
    }

    private void setGone(View buttonAddBusiness) {
        buttonAddBusiness.setVisibility(GONE);
    }
}
