package com.dinobikic.shopapp.mvp.presenters.impl;

import com.dinobikic.shopapp.mvp.presenters.MainPresenter;
import com.dinobikic.shopapp.mvp.views.MainView;
import com.dinobikic.shopapp.utils.ConnectivityHelper;

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
        if (ConnectivityHelper.isNFCEnabled()) {
            view.initUIForEnabledNFC();
        } else {
            view.initUIForDisabledNFC();
        }
    }

    @Override
    public void cancel() {

    }
}
