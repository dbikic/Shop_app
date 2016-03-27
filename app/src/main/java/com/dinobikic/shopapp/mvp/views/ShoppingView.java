package com.dinobikic.shopapp.mvp.views;

import com.dinobikic.shopapp.models.BeaconDiscount2;

import java.util.ArrayList;

/**
 * Created by dino on 23/03/16.
 */
public interface ShoppingView extends BaseView {

    void initUI();

    void requestEnableBluetooth();

    void setStoreTitle(String store);

    void showActiveDiscounts(ArrayList<BeaconDiscount2> beacons);

    void showDiscount(BeaconDiscount2 beaconDiscount);

}
