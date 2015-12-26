package com.dinotest.dinogimbaltest.activities;

import com.dinotest.dinogimbaltest.R;
import com.dinotest.dinogimbaltest.mvp.views.BaseView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by dino on 26/12/15.
 */
public class BaseActivity extends AppCompatActivity implements BaseView {


    private ProgressDialog progressDialog;

    private AlertDialog errorDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
