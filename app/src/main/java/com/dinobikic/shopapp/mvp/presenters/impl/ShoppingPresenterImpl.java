package com.dinobikic.shopapp.mvp.presenters.impl;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.interfaces.BeaconsCallback;
import com.dinobikic.shopapp.models.Discount;
import com.dinobikic.shopapp.models.StoreConfiguration;
import com.dinobikic.shopapp.mvp.interactors.ShoppingInteractor;
import com.dinobikic.shopapp.mvp.interactors.impl.ConfigInteractorImpl;
import com.dinobikic.shopapp.mvp.interactors.impl.ShoppingInteractorImpl;
import com.dinobikic.shopapp.mvp.presenters.ShoppingPresenter;
import com.dinobikic.shopapp.mvp.views.ShoppingView;
import com.dinobikic.shopapp.utils.StringUtils;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by dino on 23/03/16.
 */
public class ShoppingPresenterImpl implements ShoppingPresenter {

    ShoppingView view;

    ShoppingInteractor interactor;

    private String shopName;

    StoreConfiguration storeConfiguration;

    ArrayList<Discount> discoveredBeacons;

    int selectedPosition;

    int maxRssi;

    public ShoppingPresenterImpl(ShoppingView view) {
        this.view = view;
        this.interactor = new ShoppingInteractorImpl();
    }

    @Override
    public void onCreated(Intent intent) {

        discoveredBeacons = new ArrayList<>();
        view.initUI();
        // dinonfc://dino/shop/X
        if (intent.getDataString() != null) {
            shopName = intent.getDataString().substring(20);
        } else {
            shopName = "2";
        }

        maxRssi = new ConfigInteractorImpl().getRssi() * -1;

        getBeaconList();
    }

    @Override
    public void getBeaconList() {
        view.showProgress();
        interactor.getBeacons(shopName, new BeaconsCallback() {
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
                    view.initStore(storeConfiguration.getStore());
                }
            }

        });
    }

    @Override
    public void onDiscountSelected(int position) {
        selectedPosition = position;
        view.navigateToDiscountDetails(discoveredBeacons.get(position));
    }

    @Override
    public void onCodeReceived(String code) {
        discoveredBeacons.get(selectedPosition).setCode(code);
    }

    @Override
    public StoreConfiguration getStoreConfiguration() {
        return storeConfiguration;
    }

    @Override
    public void onBeaconDiscovered(int rssi, BluetoothDevice device) {

        if (-rssi <= -maxRssi) {
            Discount discoveredBeacon = storeConfiguration.getDiscountFromBeaconId(device.getAddress());

            if (discoveredBeacon != null && !discoveredBeacons.contains(discoveredBeacon)) {
                discoveredBeacons.add(discoveredBeacon);
                view.showDiscount(discoveredBeacon);
            }
        }

    }

    @Override
    public void cancel() {

    }
}
