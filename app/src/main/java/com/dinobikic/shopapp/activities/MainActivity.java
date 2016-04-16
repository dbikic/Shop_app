package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.mvp.presenters.MainPresenter;
import com.dinobikic.shopapp.mvp.presenters.impl.MainPresenterImpl;
import com.dinobikic.shopapp.mvp.views.MainView;
import com.dinobikic.shopapp.utils.Events;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.tvMessage)
    TextView tvMessage;

    @Bind(R.id.btnActivateNfc)
    Button btnActivateNfc;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImpl(this);
        presenter.checkNFC();
    }


    @Override
    public void initUIForEnabledNFC() {
        tvMessage.setText(R.string.main_screen_message);
        btnActivateNfc.setVisibility(View.GONE);
    }

    @Override
    public void initUIForDisabledNFC() {
        btnActivateNfc.setVisibility(View.VISIBLE);
        tvMessage.setText(R.string.main_screen_nfc_error);
    }

    @OnClick(R.id.btnActivateNfc)
    public void activateNfc() {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
        } else {
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    @OnClick(R.id.tvMessage)
    public void goToShop() {
        startActivity(new Intent(this, ShoppingActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ConfigActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Eventbus event that is triggered when the NFC state changes
    public void onEventMainThread(Events.NFCStateChange event) {
        presenter.checkNFC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkNFC();
    }

}
