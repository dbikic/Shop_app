package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Communication;
import com.gimbal.android.CommunicationListener;
import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Push;
import com.gimbal.android.Visit;

import java.sql.Date;
import java.util.Collection;
import java.util.List;


public class BeaconActivity extends Activity {

    private TextView txtShop, txtBeaconInfo;
    private BeaconEventListener moj_listener;
    private BeaconManager manager;
    private PlaceEventListener placeEventListener;
    private CommunicationListener communicationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        Gimbal.setApiKey(this.getApplication(), "b004f8c0-d82f-4809-8b0c-a3698e0b8d79");
        //Toast.makeText(getApplication(), "qq", Toast.LENGTH_SHORT).show();

        txtShop = (TextView) findViewById(R.id.txtShop);
        txtBeaconInfo = (TextView) findViewById(R.id.txtBeaconInfo);
        Intent shopIntent = getIntent();

        // dinonfc://dino/shop/X
        String shopName = shopIntent.getDataString().substring(20);

        //Integer shopId = shopIntent.getIntExtra("shopId", 0);

        txtShop.setText("Trgovina: " + shopName);



        manager = new BeaconManager();
        moj_listener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);

                if(beaconSighting.getRSSI() > -50){
                    Beacon moj = beaconSighting.getBeacon();
                   // Toast.makeText(getApplication(), "ID: " + moj.getName().toString() + "R: " + beaconSighting.getRSSI() + "T: " + moj.getTemperature(), Toast.LENGTH_LONG).show();
                    txtBeaconInfo.setText("ID: " + moj.getName().toString() + "\nSnaga: " + beaconSighting.getRSSI());
                }
                else
                {
                    txtBeaconInfo.setText("Signal slabiji od -50");
                }

            }
        };
        manager.addListener(moj_listener);
        manager.startListening();
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_beacon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
