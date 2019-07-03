package com.fulltime.foodex.mask;

/*
MIT License
Copyright (c) 2016 Diego Yasuhiko Kurisaki
*/

/* Example:
  mEmailView.addTextChangedListener(new MaskWatcher("###-##"));
*/

import android.text.Editable;
import android.text.TextWatcher;

public class MaskWatcher implements TextWatcher {
    private final String mask;
    private boolean isRunning = false;
    private boolean isDeleting = false;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    public static MaskWatcher buildCpf() {
        return new MaskWatcher("###.###.###-##");
    }

    public static MaskWatcher buildPhone() {
        return new MaskWatcher("(##) ####-####");
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
        if (isRunning || isDeleting) {
            return;
        }
        isRunning = true;

        int editableLength = editable.length();
        if (editableLength < mask.length()) {
            if (mask.charAt(editableLength) != '#') {
                editable.append(mask.charAt(editableLength));
            } else if (mask.charAt(editableLength - 1) != '#') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength);
            }
        }

        isRunning = false;
    }
}