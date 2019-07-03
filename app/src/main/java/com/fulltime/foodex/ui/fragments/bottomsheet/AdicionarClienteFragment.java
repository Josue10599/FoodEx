package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.formatter.FormataTelefone;
import com.fulltime.foodex.mask.MaskWatcher;
import com.fulltime.foodex.model.Cliente;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AdicionarClienteFragment extends BottomSheetDialogFragment {

    private final ClienteImplementado clienteImplementado;

    private final Cliente cliente;

    private View bottomSheetAdicionarCliente;
    private TextInputLayout campoNome;
    private TextInputLayout campoSobrenome;
    private TextInputLayout campoTelefone;
    private TextInputLayout campoEmail;
    private TextInputLayout campoCpf;
    private MaterialButton buttonCadastrar;

    public AdicionarClienteFragment(ClienteImplementado clienteImplementado) {
        this.clienteImplementado = clienteImplementado;
        this.cliente = new Cliente();
    }

    public AdicionarClienteFragment(Cliente cliente, ClienteImplementado clienteImplementado) {
        this.clienteImplementado = clienteImplementado;
        this.cliente = cliente;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bottomSheetAdicionarCliente = inflater.inflate
                (R.layout.fragment_bottom_sheet_add_cliente, container, false);
        bindCampo();
        configuraBotao();
        if (cliente.clienteEhCadastrado()) {
            TextView titulo = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_title_add_cliente);
            titulo.setText(R.string.alter_cliente);
            buttonCadastrar.setText(R.string.alter_cliente_button);
            populaCampos();
        }
        return bottomSheetAdicionarCliente;
    }

    private void populaCampos() {
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

    private void bindCampo() {
        campoNome = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_nome);
        campoSobrenome = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_sobrenome);
        campoTelefone = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_telefone);
        Objects.requireNonNull(campoTelefone.getEditText()).addTextChangedListener(MaskWatcher.buildPhone());
        campoTelefone.getEditText().setOnFocusChangeListener(new FormataTelefone());
        campoEmail = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_email);
        campoCpf = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_cpf);
        Objects.requireNonNull(campoCpf.getEditText()).addTextChangedListener(MaskWatcher.buildCpf());
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
        if (textoNome.matches("([A-Z])\\w+.+")) {
            cliente.setNome(textoNome);
        }
        campoNome.setError("Campo Obrigatório");
    }

    private void configuraBotao() {
        buttonCadastrar = bottomSheetAdicionarCliente.findViewById(R.id.bottom_sheet_botao_cadastrar);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (implementaCliente()) {
                    clienteImplementado.clienteImplementado(cliente);
                    dismiss();
                }
            }
        });
    }

    public interface ClienteImplementado {
        void clienteImplementado(Cliente cliente);
    }

}
