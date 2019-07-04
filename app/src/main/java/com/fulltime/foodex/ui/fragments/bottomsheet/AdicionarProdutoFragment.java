package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.mask.MaskDinheiroWatcher;
import com.fulltime.foodex.model.Produto;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AdicionarProdutoFragment extends BottomSheetDialogFragment {

    private final Produto produto;
    private final SalvaProdutoListener listener;
    private TextInputLayout textInputLayoutProdutoValor;
    private TextInputLayout textInputLayoutProdutoNome;

    public AdicionarProdutoFragment(Produto produto, SalvaProdutoListener listener) {
        this.produto = produto;
        this.listener = listener;
    }

    public AdicionarProdutoFragment(SalvaProdutoListener listener) {
        this.produto = new Produto();
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetAddProduto = inflater.inflate(R.layout.fragment_bottom_sheet_add_produto, container, false);
        configuraCampoNome(bottomSheetAddProduto);
        configuraCampoValor(bottomSheetAddProduto);
        configuraBotaoCadastrar(bottomSheetAddProduto);
        return bottomSheetAddProduto;
    }

    private void configuraBotaoCadastrar(View bottomSheetAddProduto) {
        MaterialButton buttonCadastrarProduto = bottomSheetAddProduto.findViewById(R.id.bottom_sheet_botao_cadastrar_produto);
        buttonCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCampoNome() && getCampoProduto() && produto.produtoPreenchido()) {
                    listener.produtoSalva(produto);
                    dismiss();
                }
            }
        });
    }

    private boolean getCampoNome() {
        String textProdutoValor = textInputLayoutProdutoValor.getEditText().getText().toString();
        if (textProdutoValor.isEmpty()) {
            textInputLayoutProdutoValor.setError(getString(R.string.error_campo_obrigatorio));
            return false;
        } else
            produto.setValor(textProdutoValor);
        return true;
    }

    private boolean getCampoProduto() {
        String textProdutoNome = textInputLayoutProdutoNome.getEditText().getText().toString();
        if (textProdutoNome.isEmpty()) {
            textInputLayoutProdutoNome.setError(getString(R.string.error_campo_obrigatorio));
            return false;
        } else
            produto.setNome(textProdutoNome);
        return true;
    }

    private void configuraCampoValor(View bottomSheetAddProduto) {
        textInputLayoutProdutoValor = bottomSheetAddProduto.findViewById(R.id.bottom_sheet_produto_valor);
        Objects.requireNonNull(textInputLayoutProdutoValor.getEditText())
                .addTextChangedListener(MaskDinheiroWatcher.buildDinheiro());
        Objects.requireNonNull(textInputLayoutProdutoValor.getEditText())
                .setOnFocusChangeListener(MaskDinheiroWatcher.buildDinheiro());
    }

    private void configuraCampoNome(View bottomSheetAddProduto) {
        textInputLayoutProdutoNome = bottomSheetAddProduto.findViewById(R.id.bottom_sheet_produto_nome);
    }

    public interface SalvaProdutoListener {
        void produtoSalva(Produto produto);
    }
}
