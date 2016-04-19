package com.dinobikic.shopapp.mvp.interactors.impl;

import com.google.gson.Gson;

import com.dinobikic.shopapp.ShopApplication;
import com.dinobikic.shopapp.interfaces.StoreCallback;
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

    StoreCallback storeCallback;

    @Override
    public void getStoreConfiguration(String shopName, StoreCallback storeCallback) {
        this.shopId = shopName;
        this.storeCallback = storeCallback;
        TelephonyManager telephonyManager = (TelephonyManager) ShopApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
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
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (storeConfiguration == null) {
                storeCallback.onError();
            } else {
                storeCallback.onSuccess(storeConfiguration);
            }
        }
    }


}
