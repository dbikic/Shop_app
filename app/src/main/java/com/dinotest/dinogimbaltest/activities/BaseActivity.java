package com.dinotest.dinogimbaltest.activities;

import com.dinotest.dinogimbaltest.R;
import com.dinotest.dinogimbaltest.mvp.views.BaseView;
import com.dinotest.dinogimbaltest.utils.BluetoothHelper;
import com.dinotest.dinogimbaltest.utils.Events;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by dino on 26/12/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressDialog progressDialog;

    private AlertDialog errorDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BluetoothHelper.isBluetoothEnabled()) {
            showEnableFeature(getString(R.string.bluetooth_error));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void showProgress() {
        if (!isFinishing() && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (!isFinishing() && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        if (!isFinishing()) {
            if (errorDialog == null) {
                errorDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.app_name)
                        .setMessage(message)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

            } else if (!errorDialog.isShowing()) {
                errorDialog.setMessage(message);
            } else {
                return; // Don't want to override or show double error dialog
            }
            errorDialog.show();
        }
    }

    @Override
    public void showEnableFeature(final String message) {
        if (!isFinishing()) {
            errorDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(message)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (BluetoothHelper.isBluetoothEnabled()) {
                                dialog.dismiss();
                            } else {
                                showEnableFeature(message);
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Exit the app
                            Intent goToHome = new Intent(Intent.ACTION_MAIN);
                            goToHome.addCategory(Intent.CATEGORY_HOME);
                            goToHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(goToHome);
                        }
                    })
                    .setNeutralButton(getString(R.string.bluetooth_turn_on), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Opens menu to enable bluetooth
                            Intent intentBluetooth = new Intent();
                            intentBluetooth.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
                            startActivity(intentBluetooth);
                        }
                    })
                    .setMessage(message)
                    .create();
            errorDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    // Eventbus event that is triggered when the app goes in background.
    public void onEventMainThread(Events.BluetoothState event) {
        if (event.getState().equals(getString(R.string.bluetooth_error))) {
            showEnableFeature(event.getState());
        }
    }
}
