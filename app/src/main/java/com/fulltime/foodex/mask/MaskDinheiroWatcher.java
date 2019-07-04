package com.fulltime.foodex.mask;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import static android.view.View.OnFocusChangeListener;

public class MaskDinheiroWatcher implements TextWatcher, OnFocusChangeListener {
    private static final String CENT = ",";
    private static final String EMPTY = "";

    private boolean isRunning = false;
    private boolean isDeleting = false;

    public MaskDinheiroWatcher() {
    }

    public static MaskDinheiroWatcher buildDinheiro() {
        return new MaskDinheiroWatcher();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        isDeleting = count > after;
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
        int editableLength = editable.length();
        if (editableLength == 1) {
            editable.insert(0, "0,0");
        } else {
            if (editableLength == 5 && editable.charAt(0) == '0') {
                editable.replace(0, 1, "");
            }
        }
        insertCent(editable);
        insertZero(editable);
        isRunning = false;
    }

    private void insertZero(Editable editable) {
        if (editable.charAt(0) == ',') editable.insert(0, "0");
        if (editable.subSequence(0, editable.length()).toString().equals("0,") ||
                editable.subSequence(0, editable.length()).toString().equals("0,0")) {
            editable.clear();
            editable.append("0,00");
        }
    }

    private void insertCent(Editable editable) {
        int editableLength = editable.length();
        if (editableLength > 2) {
            if (editableLength > 3 && editable.toString().charAt(editableLength - 4) == CENT.charAt(0))
                editable.replace(editableLength - 4, editableLength - 3, EMPTY);
            if (editable.charAt(editable.length() - 1) != CENT.charAt(0) &&
                    editable.charAt(editable.length() - 2) != CENT.charAt(0) &&
                    editable.charAt(editable.length() - 3) != CENT.charAt(0))
                editable.insert(editable.length() - 2, CENT);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            TextInputEditText textInputEditText = (TextInputEditText) v;
            String text = textInputEditText.getText().toString();
            if (text.matches("([0-9]{2})"))
                textInputEditText.getText().insert(0, "0,");
            if (text.matches("([0-9]+),([0-9]{1})"))
                textInputEditText.getText().append("0");
            if (text.matches("([0-9]+),"))
                textInputEditText.getText().append("00");
        }
    }
}
