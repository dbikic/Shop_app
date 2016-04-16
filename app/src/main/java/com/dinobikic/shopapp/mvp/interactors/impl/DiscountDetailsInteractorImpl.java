package com.dinobikic.shopapp.mvp.interactors.impl;

import com.google.gson.Gson;

import com.dinobikic.shopapp.ShopApplication;
import com.dinobikic.shopapp.interfaces.DiscountCodeCallback;
import com.dinobikic.shopapp.models.CodeResponse;
import com.dinobikic.shopapp.mvp.interactors.DiscountDetailsInteractor;
import com.dinobikic.shopapp.utils.Constants;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

/**
 * Created by dino on 27/03/16.
 */
public class DiscountDetailsInteractorImpl implements DiscountDetailsInteractor {

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
            OkHttpClient okHttpClient = new OkHttpClient();

            Gson gson = new Gson();

            try {
                FormEncodingBuilder formBodyBuilder = new FormEncodingBuilder();
                formBodyBuilder.add("discount_id", String.valueOf(discountId));
                formBodyBuilder.add("uid", deviceId);

                RequestBody formBody = formBodyBuilder.build();


                Request request = new Request.Builder()
                        .url(Constants.getCode())
                        .post(formBody)
                        .method("POST", formBody)
                        .build();

                Response serverResponse = okHttpClient.newCall(request).execute();
                codeResponse = gson.fromJson(serverResponse.body().string(), CodeResponse.class);
                status = true;
            } catch (Exception e) {
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
