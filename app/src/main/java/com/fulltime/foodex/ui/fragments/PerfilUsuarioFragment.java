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
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Empresa;
import com.fulltime.foodex.ui.fragments.dialog.EditaEmpresaFragment;
import com.fulltime.foodex.ui.image.ImageLoader;
import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.fulltime.foodex.R.id;
import static com.fulltime.foodex.R.layout;
import static com.fulltime.foodex.R.string;

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
        configuraBotaoLogout();
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
        loading.setVisibility(GONE);
    }

    private void configuraBotaoLogout() {
        ImageButton logout = perfilUsuarioFragment.findViewById(id.fragment_perfil_config_button);
        logout.setOnClickListener(view -> openDialogConfig());
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
        MaterialTextView textViewEmpresaNome = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_nome);
        MaterialTextView textViewEmpresaTelefone = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_telefone);
        MaterialTextView textViewEmpresaEmail = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_email);
        MaterialTextView textViewEmpresaEndereco = perfilUsuarioFragment.findViewById(id.fragment_perfil_dados_empresa_endereco);
        textViewEmpresaNome.setVisibility(VISIBLE);
        textViewEmpresaTelefone.setVisibility(VISIBLE);
        textViewEmpresaEmail.setVisibility(VISIBLE);
        textViewEmpresaEndereco.setVisibility(VISIBLE);
        textViewEmpresaNome.setText(formataTexto(string.company_name, empresa.getNomeEmpresa()));
        textViewEmpresaTelefone.setText(formataTexto(string.company_phone, empresa.getTelefoneEmpresa()));
        textViewEmpresaEmail.setText(formataTexto(string.company_email, empresa.getEmailEmpresa()));
        textViewEmpresaEndereco.setText(formataTexto(string.company_address, empresa.getEnderecoEmpresa()));
    }

    private String formataTexto(int p, String nomeEmpresa) {
        return String.format(getString(p), nomeEmpresa);
    }
}
