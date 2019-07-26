package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.mask.MoneyMaskWatcher;
import com.fulltime.foodex.model.Produto;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ImplementaProdutoFragment extends BottomSheetDialogFragment {

    private final Produto produto;
    private TextInputLayout textInputLayoutProdutoValor;
    private TextInputLayout textInputLayoutProdutoNome;
    private TextInputLayout textInputLayoutProdutoDescricao;
    private MaterialButton buttonCadastrarProduto;

    public ImplementaProdutoFragment(Produto produto) {
        this.produto = produto;
    }

    public ImplementaProdutoFragment() {
        this.produto = new Produto();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetAddProduto = inflater.inflate(R.layout.fragment_bottom_sheet_add_produto, container, false);
        configuraCampoNome(bottomSheetAddProduto);
        configuraCampoValor(bottomSheetAddProduto);
        configuraCampoDescricao(bottomSheetAddProduto);
        configuraBotaoCadastrar(bottomSheetAddProduto);
        if (produto.produtoPreenchido()) {
            preencheCampos();
            TextView titulo = bottomSheetAddProduto.findViewById(R.id.bottom_sheet_title_add);
            titulo.setText(R.string.changing_product);
            buttonCadastrarProduto.setText(R.string.change_product);
        }
        return bottomSheetAddProduto;
    }

    private void preencheCampos() {
        Objects.requireNonNull(textInputLayoutProdutoNome.getEditText()).setText(produto.getNome());
        Objects.requireNonNull(textInputLayoutProdutoValor.getEditText()).setText(produto.getValor());
        Objects.requireNonNull(textInputLayoutProdutoDescricao.getEditText()).setText(produto.getDescricao());
    }

    private void configuraBotaoCadastrar(View bottomSheetAddProduto) {
        buttonCadastrarProduto = bottomSheetAddProduto
                .findViewById(R.id.bottom_sheet_botao_cadastrar_produto);
        buttonCadastrarProduto.setOnClickListener(v -> {
            getCampoNomeProduto();
            getCampoValorProduto();
            getCampoDescricaoProduto();
            if (produto.produtoPreenchido()) {
                UpdateData.atualizaProduto(produto);
                dismiss();
            }
        });
    }

    private void getCampoValorProduto() {
        String textProdutoValor = Objects.requireNonNull(textInputLayoutProdutoValor.getEditText()).getText().toString();
        if (textProdutoValor.isEmpty()) {
            textInputLayoutProdutoValor.setError(getString(R.string.error_campo_obrigatorio));
            return;
        }
        produto.setValor(textProdutoValor);
    }

    private void getCampoNomeProduto() {
        String textProdutoNome = Objects.requireNonNull(textInputLayoutProdutoNome.getEditText()).getText().toString();
        if (textProdutoNome.isEmpty()) {
            textInputLayoutProdutoNome.setError(getString(R.string.error_campo_obrigatorio));
            return;
        }
        produto.setNome(textProdutoNome);
    }

    private void getCampoDescricaoProduto() {
        String textProdutoDescricao = Objects.requireNonNull(textInputLayoutProdutoDescricao.getEditText()).getText().toString();
        if (textProdutoDescricao.isEmpty()) {
            produto.setDescricao(getString(R.string.produto_sem_descricao));
            return;
        }
        produto.setDescricao(textProdutoDescricao);
    }

    private void configuraCampoValor(View bottomSheetAddProduto) {
        textInputLayoutProdutoValor = bottomSheetAddProduto
                .findViewById(R.id.bottom_sheet_produto_valor);
        Objects.requireNonNull(textInputLayoutProdutoValor.getEditText())
                .addTextChangedListener(MoneyMaskWatcher.buildMoney());
        Objects.requireNonNull(textInputLayoutProdutoValor.getEditText())
                .setOnFocusChangeListener(MoneyMaskWatcher.buildMoney());
    }

    private void configuraCampoNome(View bottomSheetAddProduto) {
        textInputLayoutProdutoNome = bottomSheetAddProduto
                .findViewById(R.id.bottom_sheet_produto_nome);
    }

    private void configuraCampoDescricao(View bottomSheetAddProduto) {
        textInputLayoutProdutoDescricao = bottomSheetAddProduto
                .findViewById(R.id.bottom_sheet_produto_descricao);
    }
}
