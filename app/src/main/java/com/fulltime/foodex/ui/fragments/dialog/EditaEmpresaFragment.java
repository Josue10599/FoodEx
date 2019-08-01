package com.fulltime.foodex.ui.fragments.dialog;

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

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.firebase.ui.auth.AuthUI;
import com.fulltime.foodex.R;
import com.fulltime.foodex.formatter.FormataTelefone;
import com.fulltime.foodex.helper.update.ChangeEmpresa;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.mask.MaskWatcher;
import com.fulltime.foodex.model.Empresa;
import com.fulltime.foodex.ui.activity.SignInActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import static com.fulltime.foodex.model.Empresa.VAZIO;

public class EditaEmpresaFragment extends DialogFragment {
    private Empresa empresa = new Empresa();
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextEndereco;
    private EditText editTextTelefone;

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
        UpdateData.getEmpresa();
        return dialogConfiguraDados;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Dialog dialog = getDialog();
        if (dialog != null) {
            Objects.requireNonNull(dialog.getWindow()).
                    setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getEmpresa(Empresa empresa) {
        this.empresa = empresa;
        setDataEmpresa();
    }

    @Subscribe
    public void sucessChange(ChangeEmpresa empresa) {
        dismiss();
    }

    private void setDataEmpresa() {
        empresaSetText(empresa.getEnderecoEmpresa(), editTextEndereco);
        empresaSetText(empresa.getEmailEmpresa(), editTextEmail);
        empresaSetText(empresa.getTelefoneEmpresa(), editTextTelefone);
        empresaSetText(empresa.getNomeEmpresa(), editTextNome);
    }

    private void configuraBotaoLogout(View view) {
        MaterialButton buttonLogout = view.findViewById(R.id.fragment_dialog_logout);
        buttonLogout.setOnClickListener(view1 -> logoutApp());
    }

    private void configuraBotaoSalvar(View view) {
        MaterialButton buttonSave = view.findViewById(R.id.fragment_dialog_save);
        buttonSave.setOnClickListener(view1 -> dataSaveEmpresa());
    }

    private void dataSaveEmpresa() {
        empresa.setNomeEmpresa(editTextNome.getText().toString());
        empresa.setTelefoneEmpresa(editTextTelefone.getText().toString());
        empresa.setEmailEmpresa(editTextEmail.getText().toString());
        empresa.setEnderecoEmpresa(editTextEndereco.getText().toString());
        UpdateData.setEmpresa(empresa);
    }

    private void configuraEnderecoEmpresa(View view) {
        MaterialButton buttonConfiguraEnderecoEmpresa = view.findViewById(R.id.fragment_dialog_configura_endereco_empresa);
        TextInputLayout inputLayoutEnderecoEmpresa = view.findViewById(R.id.fragment_dialog_endereco_empresa);
        editTextEndereco = inputLayoutEnderecoEmpresa.getEditText();
        buttonConfiguraEnderecoEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutEnderecoEmpresa));
    }

    private void configuraEmailEmpresa(View view) {
        MaterialButton buttonConfiguraEmailEmpresa = view.findViewById(R.id.fragment_dialog_configura_email_empresa);
        TextInputLayout inputLayoutEmailEmpresa = view.findViewById(R.id.fragment_dialog_email_empresa);
        editTextEmail = inputLayoutEmailEmpresa.getEditText();
        buttonConfiguraEmailEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutEmailEmpresa));
    }

    private void configuraTelefoneEmpresa(View view) {
        MaterialButton buttonConfiguraTelefoneEmpresa = view.findViewById(R.id.fragment_dialog_configura_telefone_empresa);
        TextInputLayout inputLayoutTelefoneEmpresa = view.findViewById(R.id.fragment_dialog_telefone_empresa);
        editTextTelefone = inputLayoutTelefoneEmpresa.getEditText();
        editTextTelefone.setOnFocusChangeListener(new FormataTelefone());
        editTextTelefone.addTextChangedListener(MaskWatcher.buildPhone());
        buttonConfiguraTelefoneEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutTelefoneEmpresa));
    }

    private void configuraNomeEmpresa(View view) {
        MaterialButton buttonConfiguraNomeEmpresa = view.findViewById(R.id.fragment_dialog_configura_nome_empresa);
        TextInputLayout inputLayoutNomeEmpresa = view.findViewById(R.id.fragment_dialog_nome_empresa);
        editTextNome = inputLayoutNomeEmpresa.getEditText();
        buttonConfiguraNomeEmpresa.setOnClickListener(view1 -> toggleTextInputLayout(inputLayoutNomeEmpresa));
    }

    private void empresaSetText(String stringEmpresa, EditText editText) {
        if (!stringEmpresa.equals(VAZIO)) {
            editText.setText(stringEmpresa);
        }
    }

    private void toggleTextInputLayout(TextInputLayout textInputLayout) {
        textInputLayout.setVisibility((textInputLayout.getVisibility() - View.GONE) * (-1));
    }

    private void logoutApp() {
        AuthUI.getInstance().signOut(Objects.requireNonNull(getContext())).addOnSuccessListener(aVoid -> {
            Intent telaLogin = new Intent(getContext(), SignInActivity.class);
            startActivity(telaLogin);
            dismiss();
            Objects.requireNonNull(getActivity()).finish();
        });
        FirebaseAuth.getInstance().signOut();
    }
}
