package com.dinotest.dinogimbaltest.activities;

import com.dinotest.dinogimbaltest.R;
import com.dinotest.dinogimbaltest.adapters.BeaconListDisplayAdapter;
import com.dinotest.dinogimbaltest.models.BeaconDiscount;
import com.dinotest.dinogimbaltest.utils.Globals;
import com.dinotest.dinogimbaltest.utils.JSONParser;
import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ShoppingActivity extends Activity {

    private BeaconEventListener beaconEventListener;

    private BeaconManager beaconManager;

    private static final String RSSIKEY = "rssiKey";

    private int rssi;

    private SharedPreferences sharedpreferences;

    private BluetoothAdapter btAdapter;

    final static int REQUEST_ENABLE_BT = 1;

    private List<BeaconDiscount> beaconDiscountList, seenBeacons;

    private BeaconListDisplayAdapter adapter;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();

    private final String STATUS_TAG = "status";

    private final String DISCOUNT_ID_TAG = "discount_id";

    private final String STORE_TAG = "store";

    private final String BEACON_ID = "factory_id";

    private final String DISCOUNT_TAG = "discountName";

    private final String PRODUCT_TAG = "discountProduct";

    private final String NEW_PRICE_TAG = "discountNewPrice";

    private final String OLD_PRICE_TAG = "discountOldPrice";

    private final String VALID_FROM_TAG = "discountValidFrom";

    private final String VALID_TO_TAG = "discountValidTo";

    private final String CODE_TAG = "code";

    private Globals globalValues;

    private String shopName;

    private ListView lvBeacons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        init();
    }

    public void init() {

        globalValues = new Globals();

        beaconDiscountList = new ArrayList<>();
        seenBeacons = new ArrayList<>();

        lvBeacons = (ListView) findViewById(R.id.lvBeacons);
        adapter = new BeaconListDisplayAdapter(this, R.layout.beacons_list_item, seenBeacons);
        lvBeacons.setAdapter(adapter);
        lvBeacons.setOnItemClickListener(beaconListClick);

        Intent shopIntent = getIntent();

        // dinonfc://dino/shop/X
        shopName = shopIntent.getDataString().substring(20);

        Gimbal.setApiKey(this.getApplication(), "b004f8c0-d82f-4809-8b0c-a3698e0b8d79");

/*
        GetConfig gc = new GetConfig();
        gc.execute();
*/
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(getApplication(), "Bluetooth nije podržan!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (btAdapter.isEnabled()) {
                GetConfig gc = new GetConfig();
                gc.execute();
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(ShoppingActivity.this, "Bluetooth upaljen!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ShoppingActivity.this, "Bez bluetooth-a aplikacija ne radi...", Toast.LENGTH_LONG).show();
            }
        }

        if (btAdapter.isEnabled()) {
            GetConfig gc = new GetConfig();
            gc.execute();
        }

    }


    private class GetConfig extends AsyncTask<Void, Void, Void> {

        private boolean status = false;

        private String newTitle;

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

            List<NameValuePair> getMethodParametars = new ArrayList<>();
            getMethodParametars.add(new BasicNameValuePair("id", shopName));
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            getMethodParametars.add(new BasicNameValuePair("user", telephonyManager.getDeviceId()));

            JSONObject jObject = jParser.makeHttpRequest(globalValues.getBeacons(), "GET", getMethodParametars);

            // todo exception ako nema neta
            Log.d("ID", telephonyManager.getDeviceId());

            Log.d("JSON", jObject.toString());

            try {

                //changeTitle(jObject.getString(STORE_TAG));
                status = jObject.getBoolean(STATUS_TAG);
                if (status) {

                    JSONArray beaconArray = jObject.getJSONArray("beacons");

                    if (beaconArray.length() > 0) {
                        for (int i = 0; i < beaconArray.length(); i++) {

                            List<NameValuePair> currentBeacon = new ArrayList<>();
                            currentBeacon.clear();

                            JSONObject beacon = beaconArray.getJSONObject(i);

                            Log.d("provjera id", beacon.getString(BEACON_ID));

                            currentBeacon.add(new BasicNameValuePair(BEACON_ID, beacon.getString(BEACON_ID)));
                            currentBeacon.add(new BasicNameValuePair(DISCOUNT_TAG, beacon.getString(DISCOUNT_TAG)));
                            currentBeacon.add(new BasicNameValuePair(DISCOUNT_ID_TAG, beacon.getString(DISCOUNT_ID_TAG)));
                            currentBeacon.add(new BasicNameValuePair(PRODUCT_TAG, beacon.getString(PRODUCT_TAG)));
                            currentBeacon.add(new BasicNameValuePair(NEW_PRICE_TAG, beacon.getString(NEW_PRICE_TAG)));
                            currentBeacon.add(new BasicNameValuePair(OLD_PRICE_TAG, beacon.getString(OLD_PRICE_TAG)));
                            currentBeacon.add(new BasicNameValuePair(VALID_FROM_TAG, beacon.getString(VALID_FROM_TAG)));
                            currentBeacon.add(new BasicNameValuePair(VALID_TO_TAG, beacon.getString(VALID_TO_TAG)));
                            currentBeacon.add(new BasicNameValuePair(CODE_TAG, beacon.getString(CODE_TAG)));

                            if (isDiscountValid(beacon.getString(VALID_FROM_TAG), beacon.getString(VALID_TO_TAG))) {
                                beaconDiscountList.add(new BeaconDiscount(currentBeacon, false));
                            }
                        }
                    }
/*
                    if (beaconDiscountList.size() == 0){
                        // todo nema popusta u ducanu
                    }
*/

                } else {
                    Log.d("STAT", "false");
                }

                newTitle = jObject.getString(STORE_TAG);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            setTitle(newTitle);
            pDialog.dismiss();

            if (status) {
                beaconSearcher();
            }
        }

    }

    public boolean isDiscountValid(String date1, String date2) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            Date dateFrom = format.parse(date1);
            Date dateTo = format.parse(date2);
            if (dateFrom.before(dateTo)) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    public void beaconSearcher() {

        sharedpreferences = getSharedPreferences("DinoShoppApp", Context.MODE_PRIVATE);
        if (sharedpreferences.contains(RSSIKEY)) {
            rssi = sharedpreferences.getInt(RSSIKEY, 0);
        } else {
            rssi = -50;
        }

        beaconManager = new BeaconManager();
        beaconEventListener = new BeaconEventListener() {

            @Override
            public void onBeaconSighting(BeaconSighting beaconSighting) {
                super.onBeaconSighting(beaconSighting);

                if (beaconSighting.getRSSI() > rssi) {
                    Beacon foundBeacon = beaconSighting.getBeacon();

                    for (int i = 0; i < beaconDiscountList.size(); i++) {

                        if (foundBeacon.getIdentifier().equals(beaconDiscountList.get(i).getId())) {
                            if (!beaconDiscountList.get(i).getSeen()) {
                                seenBeacons.add(beaconDiscountList.get(i));
                                beaconDiscountList.get(i).setSeen();

                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        };
        beaconManager.addListener(beaconEventListener);
        beaconManager.startListening();
    }


    private AdapterView.OnItemClickListener beaconListClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            globalValues.setCurrentBeacon(seenBeacons.get(i));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), DiscountDetailsActivity.class);
                    startActivity(intent);
                }
            });
        }
    };


    @Override
    public void onBackPressed() {
        beaconManager.stopListening();
        finish();
    }

    public void changeTitle(Context c, String newTitle) {

    }
}
