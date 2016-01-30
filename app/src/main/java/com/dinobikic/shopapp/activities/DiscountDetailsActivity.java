package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.models.BeaconDiscount;
import com.dinobikic.shopapp.utils.DisplayHelper;
import com.dinobikic.shopapp.utils.Globals;
import com.dinobikic.shopapp.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DiscountDetailsActivity extends Activity {

    private TextView tvCode;

    private Button btnGetCode;

    private BeaconDiscount beaconDiscount;

    private Globals globalValues;

    private ProgressDialog pDialog;

    private JSONParser jParser = new JSONParser();

    private final String STATUS_TAG = "status";

    private final String CODE_TAG = "code";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_discount_details);

        init();
    }

    public void init() {

        globalValues = new Globals();
        beaconDiscount = globalValues.getCurrentSelectedBeacon();

        TextView tvProduct = (TextView) findViewById(R.id.tvProduct);
        TextView tvOldPrice = (TextView) findViewById(R.id.tvOldPrice);
        TextView tvNewPrice = (TextView) findViewById(R.id.tvNewPrice);
        TextView tvDiscount = (TextView) findViewById(R.id.tvDiscount);
        TextView tvDiscountPercentage = (TextView) findViewById(R.id.tvDiscountPercentage);
        TextView tvValidUntil = (TextView) findViewById(R.id.tvValidUntil);
        tvCode = (TextView) findViewById(R.id.tvCode);

        tvProduct.setText(beaconDiscount.getProduct());
        tvOldPrice.setText(beaconDiscount.getOldPrice() + " kn");
        tvNewPrice.setText(beaconDiscount.getNewPrice() + " kn");
        tvDiscountPercentage.setText(DisplayHelper.getPercentage(beaconDiscount.getOldPrice(), beaconDiscount.getNewPrice()));
        tvDiscount.setText(beaconDiscount.getDiscount());
        tvValidUntil.setText(beaconDiscount.getValidTo());

        if (beaconDiscount.getCode().equals("0")) {

            btnGetCode = (Button) findViewById(R.id.btnGetCode);
            btnGetCode.setVisibility(View.VISIBLE);
            btnGetCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetCode gc = new GetCode();
                    gc.execute();
                }
            });

        } else {
            tvCode.setText("Vaš kod za popust:\n" + beaconDiscount.getCode());
            tvCode.setVisibility(View.VISIBLE);
        }

    }

    private class GetCode extends AsyncTask<Void, Void, Void> {

        private boolean status = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DiscountDetailsActivity.this);
            pDialog.setMessage("Trenutak...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<NameValuePair> getMethodParametars = new ArrayList<>();
            getMethodParametars.add(new BasicNameValuePair("discount_id", beaconDiscount.getDiscountId()));

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            getMethodParametars.add(new BasicNameValuePair("uid", telephonyManager.getDeviceId()));

            JSONObject jObject = jParser.makeHttpRequest(globalValues.getCode(), "POST", getMethodParametars);
            // todo exception ako nema neta
            Log.d("JSON", jObject.toString());

            try {
                //changeTitle(jObject.getString(STORE_TAG));
                status = jObject.getBoolean(STATUS_TAG);
                if (status) {
                    beaconDiscount.setValue(CODE_TAG, jObject.getString(CODE_TAG));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCode.setText("Vaš kod za popust:\n" + beaconDiscount.getCode());
                            btnGetCode.setVisibility(View.GONE);
                            tvCode.setVisibility(View.VISIBLE);
                        }
                    });
                }
            } catch (JSONException e) {
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