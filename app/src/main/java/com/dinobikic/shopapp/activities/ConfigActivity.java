package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.interfaces.CustomSeekBarChangeListener;
import com.dinobikic.shopapp.mvp.presenters.ConfigPresenter;
import com.dinobikic.shopapp.mvp.presenters.impl.ConfigPresenterImpl;
import com.dinobikic.shopapp.mvp.views.ConfigView;
import com.dinobikic.shopapp.utils.StringUtils;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ConfigActivity extends BaseActivity implements ConfigView {

    public static final int MAX_RSSI = 150;

    @Bind(R.id.tvRssi)
    TextView tvRssi;

    @Bind(R.id.sbRssi)
    SeekBar sbRssi;

    private ConfigPresenter presenter;

    private SeekBar.OnSeekBarChangeListener sbRssiListener = new CustomSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            presenter.setRssiValue(progress);
        }
    };

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ButterKnife.bind(this);
        presenter = new ConfigPresenterImpl(this);
        initUi();
        presenter.onCreated();
    }

    // ConfigView region

    private void initUi() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sbRssi.setMax(MAX_RSSI);
        sbRssi.setOnSeekBarChangeListener(sbRssiListener);
    }

    @Override
    public void displayRssiValue(int rssiValue) {
        tvRssi.setText(StringUtils.getRssiValueForDisplay(rssiValue));
        sbRssi.setOnSeekBarChangeListener(null);
        sbRssi.setProgress(rssiValue >= 0 ? rssiValue : -rssiValue);
        sbRssi.setOnSeekBarChangeListener(sbRssiListener);
    }

    //endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
