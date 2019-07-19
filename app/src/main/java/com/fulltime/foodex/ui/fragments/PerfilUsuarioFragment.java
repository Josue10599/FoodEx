package com.fulltime.foodex.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.authentication.Usuario;
import com.google.android.material.textview.MaterialTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

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
        MaterialTextView textViewNomeUsuario = perfilUsuarioFragment
                .findViewById(R.id.fragment_perfil_nome_usuario);
        textViewNomeUsuario.setText(usuario.getDisplayName());
        return perfilUsuarioFragment;
    }

    private void configuraFotoPerfil(View perfilUsuarioFragment) {
        ImageView fotoPerfil = perfilUsuarioFragment.findViewById(R.id.fragment_perfil_foto_usuario);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getContext())
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new CircleBitmapDisplayer())
                .build();
        ImageLoader.getInstance().displayImage(usuario.getPhotoUrl(), fotoPerfil, imageOptions);
    }
}
