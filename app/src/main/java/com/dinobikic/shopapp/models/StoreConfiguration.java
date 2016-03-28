package com.dinobikic.shopapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

    @SerializedName("telephone")
    String telephone;

    @SerializedName("storeAddress")
    String storeAddress;

    @SerializedName("beacons")
    ArrayList<Discount> discounts;

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

    public String getTelephone() {
        return telephone;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public Discount getDiscountFromBeaconId(String beaconId) {
        for (Discount discount : discounts) {
            if (discount.getBeaconFactoryId().equals(beaconId) && isDiscountValid(discount)) {
                return discount;
            }
        }
        return null;
    }

    private boolean isDiscountValid(Discount discount) {

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date discountValidTo = null;
        try {
            discountValidTo = dateFormat.parse(discount.getDiscountValidTo());
        } catch (ParseException e) {
            return false;
        }

        return now.before(discountValidTo);
    }
}
