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

public class ImplementaClienteFragment extends BottomSheetDialogFragment {

    private final ClienteImplementadoListener clienteImplementadoListener;

    private final Cliente cliente;

    private View bottomSheetAdicionarCliente;
    private TextInputLayout campoNome;
    private TextInputLayout campoSobrenome;
    private TextInputLayout campoTelefone;
    private TextInputLayout campoEmail;
    private TextInputLayout campoCpf;
    private MaterialButton buttonCadastrar;

    public ImplementaClienteFragment(ClienteImplementadoListener clienteImplementadoListener) {
        this.clienteImplementadoListener = clienteImplementadoListener;
        this.cliente = new Cliente();
    }

    public ImplementaClienteFragment(Cliente cliente, ClienteImplementadoListener clienteImplementadoListener) {
        this.clienteImplementadoListener = clienteImplementadoListener;
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
        titulo.setText(R.string.alter_cliente);
        buttonCadastrar.setText(R.string.alter_cliente_button);
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
                    clienteImplementadoListener.clienteImplementado(cliente);
                    dismiss();
                }
            }
        });
    }

    public interface ClienteImplementadoListener {
        void clienteImplementado(Cliente cliente);
    }

}
