package com.dinobikic.shopapp.mvp.interactors.impl;

import com.google.gson.Gson;

import com.dinobikic.shopapp.ShopApplication;
import com.dinobikic.shopapp.interfaces.BeaconsCallback;
import com.dinobikic.shopapp.models.StoreConfiguration;
import com.dinobikic.shopapp.mvp.interactors.ShoppingInteractor;
import com.dinobikic.shopapp.utils.Constants;
import com.dinobikic.shopapp.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dino on 23/03/16.
 */
public class ShoppingInteractorImpl implements ShoppingInteractor {

    private JSONParser jParser = new JSONParser();

    String shopId;

    String deviceId;

    BeaconsCallback beaconsCallback;

    @Override
    public void getBeacons(String shopName, BeaconsCallback beaconsCallback) {
        TelephonyManager telephonyManager = (TelephonyManager) ShopApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        this.beaconsCallback = beaconsCallback;

        // todo staviti pravi shop name
        this.shopId = "2";
        this.deviceId = telephonyManager.getDeviceId();

        GetConfig getConfig = new GetConfig();
        getConfig.execute();
    }

    private class GetConfig extends AsyncTask<Void, Void, Void> {

        StoreConfiguration storeConfiguration;

        @Override
        protected Void doInBackground(Void... voids) {

            // todo ex  ception ako nema neta

            List<NameValuePair> getMethodParametars = new ArrayList<>();
            getMethodParametars.add(new BasicNameValuePair("id", shopId));
            getMethodParametars.add(new BasicNameValuePair("user", deviceId));

            Gson gson = new Gson();

            try {
                JSONObject jObject = jParser.makeHttpRequest(Constants.getBeacons(), "GET", getMethodParametars);
                storeConfiguration = gson.fromJson(jObject.toString(), StoreConfiguration.class);
            } catch (Exception e) {
                storeConfiguration = gson.fromJson(
                        "{\n"
                                + "  \"status\": true,\n"
                                + "  \"Id\": \"2\",\n"
                                + "  \"user\": \"359775052615428\",\n"
                                + "  \"store\": \"Plodine Kostrena\",\n"
                                + "  \"beacons\": [\n"
                                + "    {\n"
                                + "      \"discount_id\": \"7\",\n"
                                + "      \"factory_id\": \"A4:D8:56:00:D1:2F\",\n"
                                + "      \"discountName\": \"Ljetnja akcija\",\n"
                                + "      \"discountProduct\": \"Suncobran\",\n"
                                + "      \"discountNewPrice\": \"10\",\n"
                                + "      \"discountOldPrice\": \"150\",\n"
                                + "      \"discountValidFrom\": \"2015-06-13 05:34:00\",\n"
                                + "      \"discountValidTo\": \"2016-06-23 17:34:00\",\n"
                                + "      \"code\": \"0\"\n"
                                + "    },\n"
                                + "    {\n"
                                + "      \"discount_id\": \"9\",\n"
                                + "      \"factory_id\": \"A4:D8:56:00:17:4A\",\n"
                                + "      \"discountName\": \"Francuski tjedan\",\n"
                                + "      \"discountProduct\": \"Vino Rose 0,7l\",\n"
                                + "      \"discountNewPrice\": \"160\",\n"
                                + "      \"discountOldPrice\": \"200\",\n"
                                + "      \"discountValidFrom\": \"2015-06-11 00:12:00\",\n"
                                + "      \"discountValidTo\": \"2016-06-12 02:12:00\",\n"
                                + "      \"code\": \"0\"\n"
                                + "    },\n"
                                + "    {\n"
                                + "      \"discount_id\": \"9\",\n"
                                + "      \"factory_id\": \"qwe\",\n"
                                + "      \"discountName\": \"Jesenska akcija\",\n"
                                + "      \"discountProduct\": \"jesen\",\n"
                                + "      \"discountNewPrice\": \"20\",\n"
                                + "      \"discountOldPrice\": \"30\",\n"
                                + "      \"discountValidFrom\": \"2015-06-11 00:12:00\",\n"
                                + "      \"discountValidTo\": \"2015-06-12 02:12:00\",\n"
                                + "      \"code\": \"0\"\n"
                                + "    },\n"
                                + "    {\n"
                                + "      \"discount_id\": \"9\",\n"
                                + "      \"factory_id\": \"qwe2\",\n"
                                + "      \"discountName\": \"Zimska akcija\",\n"
                                + "      \"discountProduct\": \"Vino Rose 0,7l\",\n"
                                + "      \"discountNewPrice\": \"160\",\n"
                                + "      \"discountOldPrice\": \"200\",\n"
                                + "      \"discountValidFrom\": \"2015-06-11 00:12:00\",\n"
                                + "      \"discountValidTo\": \"2015-06-12 02:12:00\",\n"
                                + "      \"code\": \"0\"\n"
                                + "    },\n"
                                + "    {\n"
                                + "      \"discount_id\": \"9\",\n"
                                + "      \"factory_id\": \"qwe3\",\n"
                                + "      \"discountName\": \"Najbolja akcija\",\n"
                                + "      \"discountProduct\": \"najbolji proizvod\",\n"
                                + "      \"discountNewPrice\": \"20\",\n"
                                + "      \"discountOldPrice\": \"40\",\n"
                                + "      \"discountValidFrom\": \"2015-06-11 00:12:00\",\n"
                                + "      \"discountValidTo\": \"2016-06-12 02:12:00\",\n"
                                + "      \"code\": \"0\"\n"
                                + "    }\n"
                                + "  ]\n"
                                + "}"
                        ,
                        StoreConfiguration.class);
//                beaconsCallback.onError();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            beaconsCallback.onSuccess(storeConfiguration);
        }
    }


}
