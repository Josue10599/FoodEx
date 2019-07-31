package com.fulltime.foodex.ui.fragments.bottomsheet;

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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.formatter.FormataTelefone;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.mask.MaskWatcher;
import com.fulltime.foodex.model.Cliente;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ImplementaClienteFragment extends BottomSheetDialogFragment {
    private final Cliente cliente;

    private View bottomSheetAdicionarCliente;
    private TextInputLayout campoNome;
    private TextInputLayout campoSobrenome;
    private TextInputLayout campoTelefone;
    private TextInputLayout campoEmail;
    private TextInputLayout campoCpf;
    private MaterialButton buttonCadastrar;

    public ImplementaClienteFragment() {
        this.cliente = new Cliente();
    }

    public ImplementaClienteFragment(Cliente cliente) {
        this.cliente = cliente;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomSheetAdicionarCliente = inflater.inflate
                (R.layout.fragment_bottom_sheet_add_cliente, container, false);
        bindCampos();
        if (cliente.clienteEhCadastrado()) {
            alteraInformacoesParaAlterarCliente();
            preencheCampos();
        }
        return bottomSheetAdicionarCliente;
    }

    private void alteraInformacoesParaAlterarCliente() {
        TextView titulo = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_title_add);
        titulo.setText(R.string.changing_client);
        buttonCadastrar.setText(R.string.change_client);
    }

    private void preencheCampos() {
        Objects.requireNonNull(campoNome.getEditText()).setText(cliente.getNome());
        Objects.requireNonNull(campoSobrenome.getEditText()).setText(cliente.getSobrenome());
        if (!cliente.getTelefone().isEmpty()) {
            Objects.requireNonNull(campoTelefone.getEditText()).setText(cliente.getTelefone());
        }
        Objects.requireNonNull(campoEmail.getEditText()).setText(cliente.getEmail());
        if (!cliente.getCpf().isEmpty()) {
            Objects.requireNonNull(campoCpf.getEditText()).setText(cliente.getCpf());
        }
    }

    private void bindCampos() {
        configuraCampoNome();
        configuraCampoSobrenome();
        configuraTelefone();
        configuraEmail();
        configuraCpf();
        configuraBotao();
    }

    private void configuraCpf() {
        campoCpf = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_cpf);
        Objects.requireNonNull(campoCpf.getEditText()).addTextChangedListener(MaskWatcher.buildCpf());
    }

    private void configuraEmail() {
        campoEmail = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_email);
    }

    private void configuraCampoNome() {
        campoNome = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_produto_nome);
    }

    private void configuraCampoSobrenome() {
        campoSobrenome = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_sobrenome);
    }

    private void configuraTelefone() {
        campoTelefone = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_produto_valor);
        Objects.requireNonNull(campoTelefone.getEditText()).addTextChangedListener(MaskWatcher.buildPhone());
        campoTelefone.getEditText().setOnFocusChangeListener(new FormataTelefone());
    }

    private boolean implementaCliente() {
        getTextoNome();
        cliente.setSobrenome(Objects.requireNonNull(campoSobrenome.getEditText()).getText().toString());
        cliente.setTelefone(Objects.requireNonNull(campoTelefone.getEditText()).getText().toString());
        cliente.setEmail(Objects.requireNonNull(campoEmail.getEditText()).getText().toString());
        cliente.setCpf(Objects.requireNonNull(campoCpf.getEditText()).getText().toString());
        return cliente.clienteEhCadastrado();
    }

    private void getTextoNome() {
        String textoNome = Objects.requireNonNull(campoNome.getEditText()).getText().toString();
        if (textoNome.matches("\\w+")) {
            cliente.setNome(textoNome);
            campoNome.setErrorEnabled(false);
        } else campoNome.setErrorEnabled(true);
        campoNome.setError(getString(R.string.required_field));
    }

    private void configuraBotao() {
        buttonCadastrar = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonCadastrar.setOnClickListener(v -> {
            if (implementaCliente()) {
                UpdateData.atualizaCliente(cliente);
                dismiss();
            }
        });
    }
}
