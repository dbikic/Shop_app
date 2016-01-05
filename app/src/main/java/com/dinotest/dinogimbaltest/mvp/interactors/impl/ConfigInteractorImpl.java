package com.dinotest.dinogimbaltest.mvp.interactors.impl;

import com.dinotest.dinogimbaltest.mvp.interactors.ConfigInteractor;
import com.dinotest.dinogimbaltest.utils.PreferncesUtils;

/**
 * Created by dino on 30/12/15.
 */
public class ConfigInteractorImpl implements ConfigInteractor {

    @Override
    public void setRssi(int rssi) {
        PreferncesUtils.setRssi(rssi);
    }

    @Override
    public int getRssi() {
        return PreferncesUtils.getRssi();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void reset() {

    }
}
