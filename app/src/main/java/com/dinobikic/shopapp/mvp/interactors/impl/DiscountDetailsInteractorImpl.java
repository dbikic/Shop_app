package com.dinobikic.shopapp.mvp.interactors.impl;

import com.google.gson.Gson;

import com.dinobikic.shopapp.ShopApplication;
import com.dinobikic.shopapp.interfaces.DiscountCodeCallback;
import com.dinobikic.shopapp.models.CodeResponse;
import com.dinobikic.shopapp.mvp.interactors.DiscountDetailsInteractor;
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
 * Created by dino on 27/03/16.
 */
public class DiscountDetailsInteractorImpl implements DiscountDetailsInteractor {


    private JSONParser jParser = new JSONParser();

    DiscountCodeCallback discountCodeCallback;

    int discountId;

    String deviceId;

    @Override
    public void getDiscount(int discountId, DiscountCodeCallback discountCodeCallback) {
        TelephonyManager telephonyManager = (TelephonyManager) ShopApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        this.discountId = discountId;
        this.deviceId = telephonyManager.getDeviceId();
        this.discountCodeCallback = discountCodeCallback;

        GetCode getCode = new GetCode();
        getCode.execute();
    }


    private class GetCode extends AsyncTask<Void, Void, Void> {

        CodeResponse codeResponse;

        boolean status;

        @Override
        protected Void doInBackground(Void... voids) {

            List<NameValuePair> postMethodParametars = new ArrayList<>();
            postMethodParametars.add(new BasicNameValuePair("discount_id", String.valueOf(discountId)));
            postMethodParametars.add(new BasicNameValuePair("uid", deviceId));

            Gson gson = new Gson();

            try {
                JSONObject jObject = jParser.makeHttpRequest(Constants.getCode(), "POST", postMethodParametars);
                codeResponse = gson.fromJson(jObject.toString(), CodeResponse.class);
                status = true;
            } catch (NullPointerException e) {
                status = false;
                discountCodeCallback.onError();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (status) {
                discountCodeCallback.onSuccess(codeResponse);
            }
        }
    }

}
