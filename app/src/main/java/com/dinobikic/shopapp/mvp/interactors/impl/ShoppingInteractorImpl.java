package com.dinobikic.shopapp.mvp.interactors.impl;

import com.dinobikic.shopapp.ShopApplication;
import com.dinobikic.shopapp.interfaces.GetBeaconsCallback;
import com.dinobikic.shopapp.mvp.interactors.ShoppingInteractor;
import com.dinobikic.shopapp.utils.BeaconUtils;
import com.dinobikic.shopapp.utils.Constants;
import com.dinobikic.shopapp.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dino on 23/03/16.
 */
public class ShoppingInteractorImpl implements ShoppingInteractor {

    String qweTAG = "2";

    String id;

    private JSONParser jParser = new JSONParser();

    GetBeaconsCallback getBeaconsCallback;

    @Override
    public String getBeacons(String shopName, GetBeaconsCallback getBeaconsCallback) {
        TelephonyManager telephonyManager = (TelephonyManager) ShopApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        this.getBeaconsCallback = getBeaconsCallback;
        id = telephonyManager.getDeviceId();
        GetConfig getConfig = new GetConfig();
        getConfig.execute();
        return null;
    }

    private class GetConfig extends AsyncTask<Void, Void, Void> {

        private boolean status = false;

        private String newTitle;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<NameValuePair> getMethodParametars = new ArrayList<>();
            getMethodParametars.add(new BasicNameValuePair("id", qweTAG));
            getMethodParametars.add(new BasicNameValuePair("user", id));

            JSONObject jObject = jParser.makeHttpRequest(Constants.getBeacons(), "GET", getMethodParametars);

            // todo exception ako nema neta
//            Log.d("ID", telephonyManager.getDeviceId());
//            Log.d("SHOP NAME", shopName);

            Log.d("JSON", jObject.toString());

            try {

                //changeTitle(jObject.getString(STORE_TAG));
                status = jObject.getBoolean(Constants.STATUS_TAG);
                if (status) {

                    JSONArray beaconArray = jObject.getJSONArray("beacons");

                    if (beaconArray.length() > 0) {
                        for (int i = 0; i < beaconArray.length(); i++) {

                            List<NameValuePair> currentBeacon = new ArrayList<>();
                            currentBeacon.clear();

                            JSONObject beacon = beaconArray.getJSONObject(i);

                            Log.d("provjera id", beacon.getString(Constants.BEACON_ID));

                            currentBeacon.add(new BasicNameValuePair(Constants.BEACON_ID, beacon.getString(Constants.BEACON_ID)));
                            currentBeacon.add(new BasicNameValuePair(Constants.DISCOUNT_TAG, beacon.getString(Constants.DISCOUNT_TAG)));
                            currentBeacon
                                    .add(new BasicNameValuePair(Constants.DISCOUNT_ID_TAG, beacon.getString(Constants.DISCOUNT_ID_TAG)));
                            currentBeacon.add(new BasicNameValuePair(Constants.PRODUCT_TAG, beacon.getString(Constants.PRODUCT_TAG)));
                            currentBeacon.add(new BasicNameValuePair(Constants.NEW_PRICE_TAG, beacon.getString(Constants.NEW_PRICE_TAG)));
                            currentBeacon.add(new BasicNameValuePair(Constants.OLD_PRICE_TAG, beacon.getString(Constants.OLD_PRICE_TAG)));
                            currentBeacon.add(new BasicNameValuePair(Constants.VALID_FROM_TAG, beacon.getString(Constants.VALID_FROM_TAG)));
                            currentBeacon.add(new BasicNameValuePair(Constants.VALID_TO_TAG, beacon.getString(Constants.VALID_TO_TAG)));
                            currentBeacon.add(new BasicNameValuePair(Constants.CODE_TAG, beacon.getString(Constants.CODE_TAG)));

                            if (BeaconUtils.isDiscountValid(beacon.getString(Constants.VALID_FROM_TAG), beacon.getString(Constants.VALID_TO_TAG))) {
//                                beaconDiscountList.add(new BeaconDiscount(currentBeacon, false));
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

                newTitle = jObject.getString(Constants.STORE_TAG);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getBeaconsCallback.onSuccess("asdasdas");
//            setTitle(newTitle);
//            pDialog.dismiss();

//            if (status) {
//                beaconSearcher();
//            }
        }

    }


}
