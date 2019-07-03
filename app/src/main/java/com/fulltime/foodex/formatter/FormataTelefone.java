package com.fulltime.foodex.formatter;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class FormataTelefone implements View.OnFocusChangeListener {

    private void formataTelefone(TextInputEditText v) {
        String telefoneDigitado = v.getText().toString();
        String telefoneDigitadoFormatado = telefoneDigitado;
        if (!telefoneDigitado.isEmpty()) {
            if (telefoneDigitado.matches("\\(([0-9]{2})\\) ([0-9]{4})-([0-9]{1})([0-9]{4})")) {
                telefoneDigitadoFormatado = telefoneDigitado
                        .replaceAll("\\(([0-9]{2})\\) ([0-9]{4})-([0-9]{1})([0-9]{4})", "($1) $2$3-$4");
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
