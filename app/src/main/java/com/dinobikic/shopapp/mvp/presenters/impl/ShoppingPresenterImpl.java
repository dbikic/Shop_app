package com.dinobikic.shopapp.mvp.presenters.impl;

import com.dinobikic.shopapp.interfaces.GetBeaconsCallback;
import com.dinobikic.shopapp.mvp.interactors.ShoppingInteractor;
import com.dinobikic.shopapp.mvp.interactors.impl.ShoppingInteractorImpl;
import com.dinobikic.shopapp.mvp.presenters.ShoppingPresenter;
import com.dinobikic.shopapp.mvp.views.ShoppingView;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;

/**
 * Created by dino on 23/03/16.
 */
public class ShoppingPresenterImpl implements ShoppingPresenter {

    ShoppingView view;

    ShoppingInteractor interactor;

    private String shopName;

    public ShoppingPresenterImpl(ShoppingView view) {
        this.view = view;
        this.interactor = new ShoppingInteractorImpl();
    }

    @Override
    public void onCreated(Intent intent) {

        view.initUI();
        // dinonfc://dino/shop/X
        shopName = intent.getDataString().substring(20);

        BluetoothAdapter btAdapter;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            view.showMessage("Bluetooth nije podržan!");
            view.finishActivity();
        } else {
            if (btAdapter.isEnabled()) {
                getBeaconList();
            } else {
                view.requestEnableBluetooth();
            }
        }
    }

    @Override
    public void getBeaconList() {
        view.showProgress();
        interactor.getBeacons(shopName, new GetBeaconsCallback() {
            @Override
            public void onError() {
                view.showMessage("ERROR");
            }

            @Override
            public void onSuccess(String json) {
                view.showMessage(json);
                Log.d("qwe", json);
            }
        });
    }

    @Override
    public void cancel() {

    }
}
