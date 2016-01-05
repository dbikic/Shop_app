package com.dinotest.dinogimbaltest.mvp.presenters;

/**
 * Created by dino on 30/12/15.
 */
public interface ConfigPresenter {

    void onCreated();

    void setRssiValue(int rssiValue);

    int getRssi();
}
