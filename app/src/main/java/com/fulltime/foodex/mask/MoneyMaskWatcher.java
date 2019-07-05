package com.fulltime.foodex.mask;

/*
MIT License
Copyright (c) 2019 Josue Lopes
*/

/* Example:
  editText.addTextChangedListener(MoneyMaskWatcher.buildMoney());
  editText.setOnFocusChangeListener(MoneyMaskWatcher.buildMoney());
*/

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import static android.view.View.OnFocusChangeListener;

public class MoneyMaskWatcher implements TextWatcher, OnFocusChangeListener {
    private static final String CENT = ",";
    private static final String EMPTY = "";

    private boolean isRunning = false;

    public static MoneyMaskWatcher buildMoney() {
        return new MoneyMaskWatcher();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (isRunning) {
            return;
        }
        isRunning = true;
        insertsMoneyModel(editable);
        insertCent(editable);
        insertZero(editable);
        isRunning = false;
    }

    private void insertsMoneyModel(Editable editable) {
        int editableLength = editable.length();
        if (editableLength == 1) {
            editable.insert(0, "0" + CENT + "0");
        } else if (editableLength == 5 && editable.charAt(0) == '0') {
            editable.replace(0, 1, EMPTY);
        }
    }

    private void insertCent(Editable editable) {
        int editableLength = editable.length();
        if (editableLength > 2) {
            if (checkCommaInPositionFour(editable))
                editable.replace(editableLength - 4, editableLength - 3, EMPTY);
            if (checkCommaInPositionTwo(editable)) {
                editable.replace(editableLength - 2, editableLength - 1, EMPTY);
                insertCents(editable);
            }
            if (checkIfThereIsAComma(editable))
                insertCents(editable);
        }
    }

    private void insertZero(Editable editable) {
        if (editable.charAt(0) == ',') editable.insert(0, "0");
        if (editable.subSequence(0, editable.length()).toString().equals("0" + CENT) ||
                editable.subSequence(0, editable.length()).toString().equals("0" + CENT + "0")) {
            editable.clear();
            editable.append("0" + CENT + "00");
        }
    }

    private void insertCents(Editable editable) {
        editable.insert(editable.length() - 2, CENT);
    }

    private boolean checkCommaInPositionTwo(Editable editable) {
        return editable.length() > 2 && editable.toString().charAt(editable.length() - 2) == CENT.charAt(0);
    }

    private boolean checkIfThereIsAComma(Editable editable) {
        return editable.charAt(editable.length() - 1) != CENT.charAt(0) &&
                editable.charAt(editable.length() - 2) != CENT.charAt(0) &&
                editable.charAt(editable.length() - 3) != CENT.charAt(0);
    }

    private boolean checkCommaInPositionFour(Editable editable) {
        return editable.length() > 3 && editable.toString().charAt(editable.length() - 4) == CENT.charAt(0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            TextInputEditText textInputEditText = (TextInputEditText) v;
            String text = Objects.requireNonNull(textInputEditText.getText()).toString();
            if (text.matches("([0-9]{2})"))
                textInputEditText.getText().insert(0, "0" + CENT);
            if (text.matches("([0-9]+),([0-9])"))
                textInputEditText.getText().append("0");
            if (text.matches("([0-9]+),"))
                textInputEditText.getText().append("00");
        }
    }
}
