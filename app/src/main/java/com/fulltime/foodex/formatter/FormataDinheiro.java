package com.fulltime.foodex.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

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
            return ((BigDecimal) decimalFormat.parse(valor)).setScale(2, HALF_UP);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new BigDecimal(valor.replace(',', '.')).setScale(2, HALF_UP);
    }

}
