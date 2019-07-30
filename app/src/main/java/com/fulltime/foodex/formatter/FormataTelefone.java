package com.fulltime.foodex.formatter;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class FormataTelefone implements View.OnFocusChangeListener {

    private void formataTelefone(TextInputEditText v) {
        String telefoneDigitado = Objects.requireNonNull(v.getText()).toString();
        String telefoneDigitadoFormatado = telefoneDigitado;
        if (!telefoneDigitado.isEmpty()) {
            String regex = "\\(([0-9]{2})\\) ([0-9]{4})-([0-9])([0-9]{4})";
            if (telefoneDigitado.matches(regex)) {
                telefoneDigitadoFormatado = telefoneDigitado
                        .replaceAll(regex, "($1) $2$3-$4");
            } else {
                telefoneDigitadoFormatado = "";
            }
        }
        v.setText(telefoneDigitadoFormatado);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            TextInputEditText editTextTelefone = (TextInputEditText) v;
            formataTelefone(editTextTelefone);
        }
    }
}
