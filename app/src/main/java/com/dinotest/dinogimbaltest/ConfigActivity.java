package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;


public class ConfigActivity extends Activity {

    private TextView txtBeaconInfo, txtRssi;
    private SeekBar sbProgres;
    private BeaconEventListener mojListener;
    private BeaconManager manager;
    //private PlaceEventListener placeEventListener;
   // private CommunicationListener communicationListener;
    private Integer rssi, ulazak = 0;

    private static final String RSSIKEY = "rssiKey";

    private SharedPreferences sharedpreferences;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        Gimbal.setApiKey(this.getApplication(), "b004f8c0-d82f-4809-8b0c-a3698e0b8d79");

        sharedpreferences = getSharedPreferences("DinoShoppApp", Context.MODE_PRIVATE);
        if (sharedpreferences.contains(RSSIKEY)) {
            rssi = sharedpreferences.getInt(RSSIKEY, 0);
        }
        else {
            rssi = -50;
        }


        txtBeaconInfo = (TextView) findViewById(R.id.txtBeaconInfo);
        txtRssi = (TextView) findViewById(R.id.txtRssi);
        txtRssi.setText(getResources().getString(R.string.seekLbl) + String.valueOf(rssi));

        /*
        get store id
        Intent shopIntent = getIntent();

        // dinonfc://dino/shop/X
        String shopName = shopIntent.getDataString().substring(20);

        //Integer shopId = shopIntent.getIntExtra("shopId", 0);

        txtShop.setText("Trgovina: " + shopName);
        */
        sbProgres = (SeekBar) findViewById(R.id.seekBar);
        sbProgres.setProgress(rssi * (-1));
        sbProgres.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rssi = progress * (-1);
                txtRssi.setText(getResources().getString(R.string.seekLbl) + String.valueOf(progress));

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(RSSIKEY, rssi);
                editor.apply();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        manager = new BeaconManager();
        mojListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);

                if(beaconSighting.getRSSI() > rssi){
                    Beacon moj = beaconSighting.getBeacon();
                    ulazak++;
                   // Toast.makeText(getApplication(), "ID: " + moj.getName().toString() + "R: " + beaconSighting.getRSSI() + "T: " + moj.getTemperature(), Toast.LENGTH_LONG).show();
                    txtBeaconInfo.setText("ID: " + moj.getName() + "\nRSSI: " + beaconSighting.getRSSI() + "\nUlazaka: " + String.valueOf(ulazak));
                }
                else{
                    txtBeaconInfo.setText("RSSI: " + beaconSighting.getRSSI() + ", manji od\nzadanog: " + rssi.toString());
                }

            }
        };
        manager.addListener(mojListener);
        manager.startListening();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}
