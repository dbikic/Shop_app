package com.dinotest.dinogimbaltest.mvp.presenters.impl;

import com.dinotest.dinogimbaltest.mvp.presenters.MainPresenter;
import com.dinotest.dinogimbaltest.mvp.views.MainView;
import com.dinotest.dinogimbaltest.utils.BluetoothHelper;

/**
 * Created by dino on 26/12/15.
 */
public class MainPresenterImpl implements MainPresenter {

    MainView view;

    public MainPresenterImpl(MainView view) {
        this.view = view;
    }

    @Override
    public void checkNFC() {
        if (BluetoothHelper.isNFCEnabled()) {
            view.initUIForEnabledNFC();
        } else {
            view.initUIForDisabledNFC();
        }
    }

    @Override
    public void cancel() {

    }
}
