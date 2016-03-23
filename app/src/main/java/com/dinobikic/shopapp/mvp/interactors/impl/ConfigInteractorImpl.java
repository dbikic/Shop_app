package com.dinobikic.shopapp.mvp.interactors.impl;

import com.dinobikic.shopapp.mvp.interactors.ConfigInteractor;
import com.dinobikic.shopapp.utils.PreferncesUtils;

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

}
