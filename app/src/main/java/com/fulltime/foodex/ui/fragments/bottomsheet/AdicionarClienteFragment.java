package com.fulltime.foodex.ui.fragments.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AdicionarClienteFragment extends BottomSheetDialogFragment {

    public static final String BOTTOM_SHEET_FRAGMENT_TAG = "bottom_sheet_fragment_tag";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bottomSheetAdicionarCliente = inflater.inflate
                (R.layout.fragment_bottom_sheet_add_cliente, container, false);
        return bottomSheetAdicionarCliente;
    }
}
