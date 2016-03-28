package com.dinobikic.shopapp.dialogs;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.models.StoreConfiguration;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by dino on 08/03/16.
 */
public class ShopInfoDialogFragment extends DialogFragment {

    public static final String STORE_CONFIGURATION = "storeConfiguration";

    TextView tvAddress;

    TextView tvDeposit;

    public static ShopInfoDialogFragment newInstance(StoreConfiguration storeConfiguration) {
        ShopInfoDialogFragment fragment = new ShopInfoDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(STORE_CONFIGURATION, storeConfiguration);
        fragment.setArguments(arguments);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_store_info, null, false);
        StoreConfiguration storeConfiguration = (StoreConfiguration) getArguments().getSerializable(STORE_CONFIGURATION);

        tvAddress = ButterKnife.findById(rootView, R.id.tv_address);
        tvDeposit = ButterKnife.findById(rootView, R.id.tv_telephone);

        tvAddress.setText(storeConfiguration.getStoreAddress());
        tvDeposit.setText(storeConfiguration.getTelephone());

        return new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

}
