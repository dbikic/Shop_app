package com.dinotest.dinogimbaltest;

import com.gimbal.android.Gimbal;

import android.app.Application;

/**
 * Created by dino on 26/12/15.
 */
public class ShopApplication extends Application {

    protected static ShopApplication instance;

    public static ShopApplication getInstance() {
        return instance;
    }

    public static void setInstance(ShopApplication instance) {
        ShopApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        init();
    }

    private void init() {
        Gimbal.setApiKey(this, "b004f8c0-d82f-4809-8b0c-a3698e0b8d79");
    }

}
