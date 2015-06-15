package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// Progress Dialog


public class DiscountDetailsActivity extends Activity {

    private TextView tvProduct, tvOldPrice, tvNewPrice, tvDiscount, tvCode, tvDiscountPercentage, tvValidUntil;
    private Button btnGetCode;
    private BeaconDiscount beaconDiscount;
    private Globals globalValues;
    private ProgressDialog pDialog;
    private JSONParser jParser = new JSONParser();
    private final String STATUS_TAG = "status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);

        init();
    }

    public void init() {

        globalValues = new Globals();
        beaconDiscount = globalValues.getCurrentSelectedBeacon();

        tvProduct = (TextView) findViewById(R.id.tvProduct);
        tvOldPrice = (TextView) findViewById(R.id.tvOldPrice);
        tvNewPrice = (TextView) findViewById(R.id.tvNewPrice);
        tvDiscount = (TextView) findViewById(R.id.tvDiscount);
        tvCode = (TextView) findViewById(R.id.tvCode);
        tvDiscountPercentage = (TextView) findViewById(R.id.tvDiscountPercentage);
        tvValidUntil = (TextView) findViewById(R.id.tvValidUntil);

        tvProduct.setText(beaconDiscount.getProduct());
        tvOldPrice.setText(beaconDiscount.getOldPrice());
        tvNewPrice.setText(beaconDiscount.getNewPrice());
        tvDiscountPercentage.setText(getPercentage(beaconDiscount.getOldPrice(), beaconDiscount.getNewPrice()));
        tvDiscount.setText(beaconDiscount.getDiscount());
        tvValidUntil.setText(beaconDiscount.getCode());

        if(beaconDiscount.getCode().equals("0")){
            btnGetCode = (Button) findViewById(R.id.btnGetCode);
            btnGetCode.setVisibility(View.VISIBLE);
            btnGetCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetCode gc = new GetCode();
                    gc.execute();
                }
            });
        }else{
            tvCode.setText(beaconDiscount.getCode());
            tvCode.setVisibility(View.VISIBLE);
        }

    }

    private String getPercentage(String oldPrice, String newPrice){
        return "Hardkod";
    }

    private class GetCode extends AsyncTask<Void, Void, Void> {

        private boolean status = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DiscountDetailsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<NameValuePair> getMethodParametars = new ArrayList<>();
            getMethodParametars.add(new BasicNameValuePair("discount_id",beaconDiscount.getId()));

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            getMethodParametars.add(new BasicNameValuePair("uid",telephonyManager.getDeviceId()));


            JSONObject jObject = jParser.makeHttpRequest(globalValues.getCode(), "POST", getMethodParametars);
            // todo exception ako nema neta
            Log.d("JSON", jObject.toString());

            try {

                //changeTitle(jObject.getString(STORE_TAG));
                status = jObject.getBoolean(STATUS_TAG);
                if (status) {
                    return null;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
                    return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();

        }

    }

}