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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

public class FormataDinheiro {
    private static final String FORMATACAO = "#,##0.00";
    private final DecimalFormat decimalFormat;

    public FormataDinheiro() {
        this.decimalFormat = new DecimalFormat(FORMATACAO);
        this.decimalFormat.setParseBigDecimal(true);
    }

    public String formataValor(BigDecimal valor) {
        return decimalFormat.format(valor);
    }

    public BigDecimal getBigDecimal(String valor) {
        try {
            return ((BigDecimal) Objects.requireNonNull(decimalFormat.parse(valor))).setScale(2, HALF_UP);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new BigDecimal(valor.replace(',', '.')).setScale(2, HALF_UP);
    }

}
