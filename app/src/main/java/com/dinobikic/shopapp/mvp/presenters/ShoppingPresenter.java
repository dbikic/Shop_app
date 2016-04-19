package com.dinobikic.shopapp.mvp.presenters;

import com.dinobikic.shopapp.models.StoreConfiguration;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;

/**
 * Created by dino on 23/03/16.
 */
public interface ShoppingPresenter extends BasePresenter {

    void onCreated(Intent intent);

    boolean areProtocolsEnabled();

    void getStoreInfo();

    void onBeaconDiscovered(int rssi, BluetoothDevice device);

    void onDiscountSelected(int position);

    void onCodeReceived(String code);

    StoreConfiguration getStoreConfiguration();

}
