package com.fulltime.foodex.ui.fragments.bottomsheet;

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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fulltime.foodex.R;
import com.fulltime.foodex.helper.update.UpdateData;
import com.fulltime.foodex.model.Cliente;
import com.fulltime.foodex.model.Empresa;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.fulltime.foodex.ui.fragments.bottomsheet.ConstantesBottomSheet.BOTTOM_SHEET_FRAGMENT_TAG;

public class MenuClientFragment extends BottomSheetDialogFragment {

    private static final String MAIL = "mailto:";
    private static final String SUBJECT = "?subject=";
    private static final String EMAIL_BODY = "&body=";
    private static final String SMS = "smsto:";
    private static final String SMS_BODY = "sms_body";
    private static final String PHONE = "tel:";

    private final Cliente cliente;
    private Empresa empresa;

    public MenuClientFragment(Cliente cliente) {
        this.cliente = cliente;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UpdateData.getEmpresa();
        View viewMenuClientFragment;
        viewMenuClientFragment = inflater.inflate(R.layout.fragment_bottom_sheet_menu_client, container, false);
        configButtonCall(viewMenuClientFragment);
        configButtonMessage(viewMenuClientFragment);
        configButtonEmail(viewMenuClientFragment);
        configuraButtonChangeClient(viewMenuClientFragment);
        return viewMenuClientFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    private void configuraButtonChangeClient(View viewMenuClientFragment) {
        MaterialButton buttonEdit = viewMenuClientFragment.findViewById(R.id.bottom_sheet_menu_client_button_edit);
        buttonEdit.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            new ImplementaClienteFragment(cliente).show(getFragmentManager(), BOTTOM_SHEET_FRAGMENT_TAG);
        });
    }

    private void configButtonEmail(View viewMenuClientFragment) {
        MaterialButton buttonEmail = viewMenuClientFragment.findViewById(R.id.bottom_sheet_menu_client_button_email);
        buttonEmail.setOnClickListener(view -> {
            String uriText = MAIL + Uri.encode(cliente.getEmail()) +
                    SUBJECT + Uri.encode(empresa.getNomeEmpresa());
            if (cliente.estaDevendo())
                uriText = uriText.concat(EMAIL_BODY + Uri.encode(messageForDebtor()));
            Uri uri = Uri.parse(uriText);
            Intent email = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(email);
            dismiss();
        });
    }

    private void configButtonMessage(View viewMenuClientFragment) {
        MaterialButton buttonMessage = viewMenuClientFragment.findViewById(R.id.bottom_sheet_menu_client_button_message);
        buttonMessage.setOnClickListener(view -> {
            Uri uri = Uri.parse(SMS + cliente.telefoneSemFormatacao());
            Intent message = new Intent(Intent.ACTION_SENDTO, uri);
            if (empresa != null)
                if (cliente.estaDevendo()) {
                    message.putExtra(SMS_BODY, messageForDebtor());
                }
            startActivity(message);
            dismiss();
        });
    }

    private void configButtonCall(View viewMenuClientFragment) {
        MaterialButton buttonCall = viewMenuClientFragment.findViewById(R.id.bottom_sheet_menu_client_button_call);
        buttonCall.setOnClickListener(view -> {
            Uri uri = Uri.parse(PHONE + cliente.telefoneSemFormatacao());
            Intent call = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(call);
            dismiss();
        });
    }

    private String messageForDebtor() {
        return getString(R.string.message_to_debtor_customer, empresa.getNomeEmpresa(), cliente.getValorEmDeficit());
    }
}
