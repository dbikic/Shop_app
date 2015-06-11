package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;
import com.qsl.faar.service.location.d;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ShoppingActivity extends Activity {

    private TextView txtBeaconInfo1, txtBeaconInfo2, txtBeaconInfo3;
    private BeaconEventListener mojListener;
    private BeaconManager manager;
    private static final String RSSIKEY = "rssiKey";
    private int rssi;
    private SharedPreferences sharedpreferences;
    private boolean beacon1Seen = false,beacon2Seen = false,beacon3Seen = false;
    private static final String beacon1Id = "PPCN-QWM7G", beacon2Id = "MGWV-YJA5J", beacon3Id = "6HU1-R7XS5";
    BluetoothAdapter btAdapter;
    final static int REQUEST_ENABLE_BT = 1;
    List<com.dinotest.dinogimbaltest.Beacon> beaconList;

    // Progress Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

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
/*
        GetConfig gc = new GetConfig();
        gc.execute();
*/
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter == null){
            Toast.makeText(getApplication(), "Bluetooth nije podržan!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            if(btAdapter.isEnabled()){
                GetConfig gc = new GetConfig();
                gc.execute();
            }else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode==RESULT_OK){
                Toast.makeText(ShoppingActivity.this, "Bluetooth upaljen!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(ShoppingActivity.this, "Bez bluetooth-a aplikacija ne radi...", Toast.LENGTH_LONG).show();
            }
        }

        if (btAdapter.isEnabled()) {
            GetConfig gc = new GetConfig();
            gc.execute();
        }

    }


    private class GetConfig extends AsyncTask<Void, Void, Void>{

        private boolean status = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShoppingActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            JSONObject obj = null;
            obj = jParser.makeHttpRequest("http://meri-test.webuda.com/shop_app_backend/api/getConfig.php?id=2", "GET", null);
            Log.d("JSON", obj.toString());

            try {
                status = obj.getBoolean("status");
                if(status){
                    Log.d("STAT", "true");
                    List<NameValuePair> currentBeacon = new ArrayList<NameValuePair>();

                    JSONArray beaconArray = obj.getJSONArray("beacons");

                    for(int i=0; i< beaconArray.length(); i++){
                        currentBeacon.clear();
                        JSONObject beacon = beaconArray.getJSONObject(i);

                       // currentBeacon.add(new BasicNameValuePair(ID_TAG, beacon.getString(ID_TAG)));
                        Log.d(String.valueOf(i), beacon.getString("discountName"));
                        Log.d(String.valueOf(i), beacon.getString("discountProduct"));
                    }

                }else{
                    Log.d("STAT", "false");
                }


            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();

            if(status){
                run();
            }
        }

    }

    public void run(){

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
    @Override
    public void onBackPressed() {
        manager.stopListening();
        finish();
    }
}
