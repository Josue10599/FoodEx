package com.fulltime.foodex.ui.fragments.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.firebase.ui.auth.AuthUI;
import com.fulltime.foodex.R;
import com.fulltime.foodex.ui.activity.SignInActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class EditaEmpresaFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogConfiguraDados = inflater.inflate(R.layout.fragment_dialog_configura_dados, container, false);
        configuraNomeEmpresa(dialogConfiguraDados);
        configuraTelefoneEmpresa(dialogConfiguraDados);
        configuraEmailEmpresa(dialogConfiguraDados);
        configuraEnderecoEmpresa(dialogConfiguraDados);
        configuraBotaoSalvar(dialogConfiguraDados);
        configuraBotaoLogout(dialogConfiguraDados);
        return dialogConfiguraDados;
    }

    private void configuraBotaoLogout(View view) {
        MaterialButton buttonLogout = view.findViewById(R.id.fragment_dialog_logout);
        buttonLogout.setOnClickListener(view1 -> logoutApp());
    }

    private void configuraBotaoSalvar(View view) {

    }

    private void configuraEnderecoEmpresa(View view) {
        MaterialButton buttonConfiguraEnderecoEmpresa = view.findViewById(R.id.fragment_dialog_configura_endereco_empresa);
        TextInputLayout inputLayoutEnderecoEmpresa = view.findViewById(R.id.fragment_dialog_endereco_empresa);
        buttonConfiguraEnderecoEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutEnderecoEmpresa));
    }

    private void configuraEmailEmpresa(View view) {
        MaterialButton buttonConfiguraEmailEmpresa = view.findViewById(R.id.fragment_dialog_configura_email_empresa);
        TextInputLayout inputLayoutEmailEmpresa = view.findViewById(R.id.fragment_dialog_email_empresa);
        buttonConfiguraEmailEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutEmailEmpresa));
    }

    private void configuraTelefoneEmpresa(View view) {
        MaterialButton buttonConfiguraTelefoneEmpresa = view.findViewById(R.id.fragment_dialog_configura_telefone_empresa);
        TextInputLayout inputLayoutTelefoneEmpresa = view.findViewById(R.id.fragment_dialog_telefone_empresa);
        buttonConfiguraTelefoneEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutTelefoneEmpresa));
    }

    private void configuraNomeEmpresa(View view) {
        MaterialButton buttonConfiguraNomeEmpresa = view.findViewById(R.id.fragment_dialog_configura_nome_empresa);
        TextInputLayout inputLayoutNomeEmpresa = view.findViewById(R.id.fragment_dialog_nome_empresa);
        buttonConfiguraNomeEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutNomeEmpresa));
    }

    private void toggleTextInputLayout(TextInputLayout textInputLayout) {
        textInputLayout.setVisibility((textInputLayout.getVisibility() - View.GONE) * (-1));
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void logoutApp() {
        AuthUI.getInstance().signOut(Objects.requireNonNull(getContext())).addOnSuccessListener(aVoid -> {
            Intent telaLogin = new Intent(getContext(), SignInActivity.class);
            startActivity(telaLogin);
            Objects.requireNonNull(getActivity()).finish();
        });
        FirebaseAuth.getInstance().signOut();
    }


}
