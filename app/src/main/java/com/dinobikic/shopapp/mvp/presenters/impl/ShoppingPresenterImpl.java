package com.dinobikic.shopapp.mvp.presenters.impl;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.interfaces.GetBeaconsCallback;
import com.dinobikic.shopapp.models.StoreConfiguration;
import com.dinobikic.shopapp.mvp.interactors.ShoppingInteractor;
import com.dinobikic.shopapp.mvp.interactors.impl.ShoppingInteractorImpl;
import com.dinobikic.shopapp.mvp.presenters.ShoppingPresenter;
import com.dinobikic.shopapp.mvp.views.ShoppingView;
import com.dinobikic.shopapp.utils.StringUtils;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by dino on 23/03/16.
 */
public class ShoppingPresenterImpl implements ShoppingPresenter {

    ShoppingView view;

    ShoppingInteractor interactor;

    private String shopName;

    StoreConfiguration storeConfiguration;

    public ShoppingPresenterImpl(ShoppingView view) {
        this.view = view;
        this.interactor = new ShoppingInteractorImpl();
    }

    @Override
    public void onCreated(Intent intent) {

        view.initUI();
        // dinonfc://dino/shop/X
        shopName = intent.getDataString().substring(20);
        shopName = "2";

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
                view.hideProgress();
                view.showMessage(StringUtils.getString(R.string.error));
            }

            @Override
            public void onSuccess(StoreConfiguration response) {
                view.hideProgress();
                if (response != null) {
                    storeConfiguration = response;
                    view.setStoreTitle(storeConfiguration.getStore());
                    view.showActiveDiscounts(storeConfiguration.getBeaconDiscounts());
                }
            }

        });
    }

    @Override
    public void cancel() {

    }
}
