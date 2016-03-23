package com.dinobikic.shopapp.mvp.interactors.impl;

import com.google.gson.Gson;

import com.dinobikic.shopapp.ShopApplication;
import com.dinobikic.shopapp.interfaces.GetBeaconsCallback;
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

    GetBeaconsCallback getBeaconsCallback;

    @Override
    public String getBeacons(String shopName, GetBeaconsCallback getBeaconsCallback) {
        TelephonyManager telephonyManager = (TelephonyManager) ShopApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        this.getBeaconsCallback = getBeaconsCallback;

        // todo staviti pravi shop name
        this.shopId = "2";
        this.deviceId = telephonyManager.getDeviceId();

        GetConfig getConfig = new GetConfig();
        getConfig.execute();
        return null;
    }

    String shopId;

    String deviceId;
    private class GetConfig extends AsyncTask<Void, Void, Void> {

        StoreConfiguration storeConfiguration;

        @Override
        protected Void doInBackground(Void... voids) {

            // todo exception ako nema neta

            List<NameValuePair> getMethodParametars = new ArrayList<>();
            getMethodParametars.add(new BasicNameValuePair("id", shopId));
            getMethodParametars.add(new BasicNameValuePair("user", deviceId));

            JSONObject jObject = jParser.makeHttpRequest(Constants.getBeacons(), "GET", getMethodParametars);
            Gson gson = new Gson();
            storeConfiguration = gson.fromJson(jObject.toString(), StoreConfiguration.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getBeaconsCallback.onSuccess(storeConfiguration);
        }
    }


}
