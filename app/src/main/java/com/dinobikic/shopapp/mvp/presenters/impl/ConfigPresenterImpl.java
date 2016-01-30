package com.dinobikic.shopapp.mvp.presenters.impl;

import com.dinobikic.shopapp.mvp.interactors.ConfigInteractor;
import com.dinobikic.shopapp.mvp.interactors.impl.ConfigInteractorImpl;
import com.dinobikic.shopapp.mvp.presenters.ConfigPresenter;
import com.dinobikic.shopapp.mvp.views.ConfigView;

/**
 * Created by dino on 30/12/15.
 */
public class ConfigPresenterImpl implements ConfigPresenter {

    ConfigView view;

    ConfigInteractor interactor;

    /**
     * Member that is used to cache rssi value in order to lower the number of reading from shared preferences.
     */
    int rssi = Integer.MIN_VALUE;

    public ConfigPresenterImpl(ConfigView view) {
        this.view = view;
        interactor = new ConfigInteractorImpl();
    }

    @Override
    public void onCreated() {
        view.displayRssiValue(interactor.getRssi());
    }

    @Override
    public void setRssiValue(int rssiValue) {
        this.rssi = rssiValue;
        interactor.setRssi(rssiValue);
        view.displayRssiValue(rssiValue);
    }


    @Override
    public int getRssi() {
        return rssi != Integer.MIN_VALUE ? rssi : interactor.getRssi();
    }
}
