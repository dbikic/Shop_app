package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;


public class ShoppingActivity extends Activity {

    private TextView txtBeaconInfo1, txtBeaconInfo2, txtBeaconInfo3;
    private BeaconEventListener mojListener;
    private BeaconManager manager;
    private static final String RSSIKEY = "rssiKey";
    private int rssi;
    private SharedPreferences sharedpreferences;
    private boolean beacon1Seen = false,beacon2Seen = false,beacon3Seen = false;
    private static final String beacon1Id = "PPCN-QWM7G", beacon2Id = "MGWV-YJA5J", beacon3Id = "6HU1-R7XS5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);


        txtBeaconInfo1 = (TextView) findViewById(R.id.txtBeacon1Info);
        txtBeaconInfo2 = (TextView) findViewById(R.id.txtBeacon2Info);
        txtBeaconInfo3 = (TextView) findViewById(R.id.txtBeacon3Info);
        Intent shopIntent = getIntent();

        // dinonfc://dino/shop/X
        String shopName = shopIntent.getDataString().substring(20);
        setTitle(shopName);

        Gimbal.setApiKey(this.getApplication(), "b004f8c0-d82f-4809-8b0c-a3698e0b8d79");

        sharedpreferences = getSharedPreferences("DinoShoppApp", Context.MODE_PRIVATE);
        if (sharedpreferences.contains(RSSIKEY)) {
            rssi = sharedpreferences.getInt(RSSIKEY, 0);
        }
        else {
            rssi = -50;
        }

        manager = new BeaconManager();
        mojListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);

                if(beaconSighting.getRSSI() > rssi){
                    Beacon moj = beaconSighting.getBeacon();

                    switch (moj.getIdentifier()){

                        case beacon1Id:
                            if(!beacon1Seen){
                                txtBeaconInfo1.setText("Id: " + moj.getIdentifier()
                                        + "\nRssi: " + beaconSighting.getRSSI()
                                        + "\nIme: " + moj.getName());
                                //beacon1Seen = true;
                            }
                            break;
                        case beacon2Id:
                            if(!beacon2Seen){
                                txtBeaconInfo2.setText("Vidio sam: " + moj.getIdentifier()
                                        + "\nRssi: " + beaconSighting.getRSSI()
                                        + "\nIme: " + moj.getName());
                                //beacon2Seen = true;
                            }
                            break;
                        case beacon3Id:
                            if(!beacon3Seen){
                                txtBeaconInfo3.setText("Vidio sam: " + moj.getIdentifier()
                                        + "\nRssi: " + beaconSighting.getRSSI()
                                        + "\nIme: " + moj.getName());
                                //beacon3Seen = true;
                            }
                            break;
                    }
                }
            }
        };
        manager.addListener(mojListener);
        manager.startListening();
    }


}
