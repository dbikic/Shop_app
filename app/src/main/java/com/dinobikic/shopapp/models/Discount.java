package com.dinobikic.shopapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dino on 23/03/16.
 */
public class Discount implements Serializable {

    @SerializedName("discount_id")
    int discountId;

    @SerializedName("factory_id")
    String beaconFactoryId;

    @SerializedName("discountName")
    String discountName;

    @SerializedName("discountProduct")
    String discountProduct;

    @SerializedName("discountNewPrice")
    double discountNewPrice;

    @SerializedName("discountOldPrice")
    double discountOldPrice;

    @SerializedName("discountValidFrom")
    String discountValidFrom;

    @SerializedName("discountValidTo")
    String discountValidTo;

    @SerializedName("code")
    String code;

    public int getDiscountId() {
        return discountId;
    }

    public String getBeaconFactoryId() {
        return beaconFactoryId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public String getDiscountProduct() {
        return discountProduct;
    }

    public double getDiscountNewPrice() {
        return discountNewPrice;
    }

    public double getDiscountOldPrice() {
        return discountOldPrice;
    }

    public String getDiscountValidFrom() {
        return discountValidFrom;
    }

    public String getDiscountValidTo() {
        return discountValidTo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDiscountActivated() {
        return !getCode().equals("0");
    }

}
