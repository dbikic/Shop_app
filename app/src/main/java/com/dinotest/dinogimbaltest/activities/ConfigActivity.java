package com.dinotest.dinogimbaltest.activities;

import com.dinotest.dinogimbaltest.R;
import com.dinotest.dinogimbaltest.callbacks.CustomSeekBarChangeListener;
import com.dinotest.dinogimbaltest.mvp.presenters.ConfigPresenter;
import com.dinotest.dinogimbaltest.mvp.presenters.impl.ConfigPresenterImpl;
import com.dinotest.dinogimbaltest.mvp.views.ConfigView;
import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ConfigActivity extends BaseActivity implements ConfigView {

    public static final int NEGATIVE_FACOTR = -1;

    @Bind(R.id.tvBeaconInfo)
    TextView tvBeaconInfo;

    @Bind(R.id.tvRssi)
    TextView tvRssi;

    @Bind(R.id.sbRssi)
    SeekBar sbRssi;

    private BeaconManager beaconManager;

    private ConfigPresenter presenter;

    private int ulazak = 0;

    private BeaconEventListener beaconListener = new BeaconEventListener() {
        @Override
        public void onBeaconSighting(BeaconSighting beaconSighting) {
            super.onBeaconSighting(beaconSighting);
            if (beaconSighting.getRSSI() > presenter.getRssi()) {
                Beacon moj = beaconSighting.getBeacon();
                ulazak++;
                tvBeaconInfo.setText(
                        "ID: " + moj.getName() + "\nRSSI: " + beaconSighting.getRSSI() + "\nUlazaka: " + String.valueOf(ulazak));
            } else {
                tvBeaconInfo.setText("RSSI: " + beaconSighting.getRSSI() + ", manji od\nzadanog: " + presenter.getRssi());
            }

        }
    };

    private SeekBar.OnSeekBarChangeListener sbRssiListener = new CustomSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            presenter.setRssiValue(progress);
        }
    };

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        ButterKnife.bind(this);
        presenter = new ConfigPresenterImpl(this);
        initUi();
        presenter.onCreated();
    }

    private void initUi() {
        sbRssi.setOnSeekBarChangeListener(sbRssiListener);
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconListener);
        beaconManager.startListening();
    }

    @Override
    public void displayRssiValue(int rssiValue) {
        tvRssi.setText(String.format(Locale.US, getResources().getString(R.string.rssiLabel), String.valueOf(rssiValue)));
        sbRssi.setProgress(rssiValue);
    }


//        rssi = PreferncesUtils.getRssi();

//        tvBeaconInfo = (TextView) findViewById(R.id.txtBeaconInfo);
//        tvRssi = (TextView) findViewById(R.id.txtRssi);

        /*
        get store id
        Intent shopIntent = getIntent();

        // dinonfc://dino/shop/X
        String shopName = shopIntent.getDataString().substring(20);

        //Integer shopId = shopIntent.getIntExtra("shopId", 0);

        txtShop.setText("Trgovina: " + shopName);
        */
//        sbRssi = (SeekBar) findViewById(R.id.seekBar);
//        sbRssi.setProgress(rssi * (-1));


        /*
        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                // This will be invoked when a place is entered. Example below shows a simple log upon enter
                Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
                Toast.makeText(getApplication(), "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVisitEnd(Visit visit) {
                // This will be invoked when a place is exited. Example below shows a simple log upon exit
                Toast.makeText(getApplication(), "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()), Toast.LENGTH_SHORT).show();
            }
        };
        PlaceManager.getInstance().addListener(placeEventListener);

        communicationListener = new CommunicationListener() {
            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Visit visit) {
                for (Communication comm : communications) {
                    Log.i("INFO", "Place Communication: " + visit.getPlace().getName() + ", message: " + comm.getTitle());
                    Toast.makeText(getApplication(), "Place Communication: " + visit.getPlace().getName() + ", message: " + comm.getTitle(), Toast.LENGTH_SHORT).show();
                }
                //allow Gimbal to show the notification for all communications
                return communications;
            }

            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Push push) {
                for (Communication comm : communications) {
                    Log.i("INFO", "Received a Push Communication with message: " + comm.getTitle());
                    Toast.makeText(getApplication(), "Received a Push Communication with message: " + comm.getTitle(), Toast.LENGTH_SHORT).show();
                }
                //allow Gimbal to show the notification for all communications
                return communications;
            }

            @Override
            public void onNotificationClicked(List<Communication> communications) {
                Log.i("INFO", "Notification was clicked on");
                Toast.makeText(getApplication(), "Notification was clicked on", Toast.LENGTH_SHORT).show();
            }
        };
        CommunicationManager.getInstance().addListener(communicationListener);

        PlaceManager.getInstance().startMonitoring();
        //beaconManager.startListening();
        CommunicationManager.getInstance().startReceivingCommunications();
        */
}
