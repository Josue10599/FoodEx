package com.fulltime.foodex.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.ui.activity.SignInActivity;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.Objects;

public class PerfilUsuarioFragment extends Fragment {

    private final Usuario usuario;

    public PerfilUsuarioFragment(Usuario usuario) {
        this.usuario = usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View perfilUsuarioFragment = inflater.inflate(R.layout.fragment_perfil, container, false);
        configuraFotoPerfil(perfilUsuarioFragment);
        configuraNomeUsuario(perfilUsuarioFragment);
        configuraBotaoLogout(perfilUsuarioFragment);
        return perfilUsuarioFragment;
    }

    private void configuraBotaoLogout(View perfilUsuarioFragment) {
        ImageButton logout = perfilUsuarioFragment.findViewById(R.id.fragment_perfil_config_button);
        logout.setOnClickListener(view -> logoutApp());
    }

    private void logoutApp() {

        FirebaseAuth.getInstance().signOut();
        Intent telaLogin = new Intent(getContext(), SignInActivity.class);
        startActivity(telaLogin);
        Objects.requireNonNull(getActivity()).finish();
    }

    private void configuraNomeUsuario(View perfilUsuarioFragment) {
        MaterialTextView textViewNomeUsuario = perfilUsuarioFragment
                .findViewById(R.id.fragment_perfil_nome_usuario);
        textViewNomeUsuario.setText(usuario.getDisplayName());
    }

    private void configuraFotoPerfil(View perfilUsuarioFragment) {
        ImageView fotoPerfil = perfilUsuarioFragment.findViewById(R.id.fragment_perfil_foto_usuario);
        TextDrawable drawablePadrao = TextDrawable.builder()
                .buildRound(String.valueOf(usuario.getDisplayName().charAt(0)),
                        Objects.requireNonNull(getContext())
                                .getResources().getColor(R.color.color_secondary));
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                Objects.requireNonNull(getContext()))
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
        CircularProgressDrawable drawableLoading = new CircularProgressDrawable(getContext());
        drawableLoading.setStyle(CircularProgressDrawable.DEFAULT);
        drawableLoading.start();
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .showImageForEmptyUri(drawablePadrao)
                .showImageOnFail(drawablePadrao)
                .showImageOnLoading(drawableLoading)
                .build();
        if (!usuario.getPhotoUrl().isEmpty())
            ImageLoader.getInstance().displayImage(usuario.getPhotoUrl(), fotoPerfil, imageOptions);
    }
}
