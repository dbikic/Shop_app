package com.dinobikic.shopapp.mvp.interactors.impl;

import com.google.gson.Gson;

import com.dinobikic.shopapp.ShopApplication;
import com.dinobikic.shopapp.interfaces.BeaconsCallback;
import com.dinobikic.shopapp.models.StoreConfiguration;
import com.dinobikic.shopapp.mvp.interactors.ShoppingInteractor;
import com.dinobikic.shopapp.utils.Constants;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

/**
 * Created by dino on 23/03/16.
 */
public class ShoppingInteractorImpl implements ShoppingInteractor {

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

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            try {
                Request request = new Request.Builder()
                        .url(Constants.getBeaconsUrl(shopId, deviceId))
                        .build();

                Response serverResponse = okHttpClient.newCall(request).execute();
                storeConfiguration = gson.fromJson(serverResponse.body().string(), StoreConfiguration.class);
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
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            beaconsCallback.onSuccess(storeConfiguration);
        }
    }


}
