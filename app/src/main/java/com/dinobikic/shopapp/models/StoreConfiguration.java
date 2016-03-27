package com.dinobikic.shopapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dino on 23/03/16.
 */
public class StoreConfiguration implements Serializable {

    @SerializedName("status")
    boolean status;

    @SerializedName("user")
    String user;

    @SerializedName("store")
    String store;

    @SerializedName("Id")
    String storeId;

    @SerializedName("beacons")
    ArrayList<Discount> beaconDiscounts;

    public boolean isStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

    public String getStore() {
        return store;
    }

    public String getStoreId() {
        return storeId;
    }

    public ArrayList<Discount> getBeaconDiscounts() {
        return beaconDiscounts;
    }

    public Discount getDiscountFromBeaconId(String beaconId) {
        for (Discount beaconDiscount : beaconDiscounts) {
            if (beaconDiscount.getBeaconFactoryId().equals(beaconId)) {
                return beaconDiscount;
            }
        }
        return null;
    }
}
