package com.fulltime.foodex.formatter;

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
