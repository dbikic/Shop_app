package com.dinobikic.shopapp.utils;


import com.dinobikic.shopapp.models.BeaconDiscount;

import java.util.Locale;

public final class Constants {

    public static String serverUrl = "http://cons.riteh.hr/nfc/api/";

    public static BeaconDiscount currentSelectedBeacon ;

    public Constants() {
    }


    public static String getBeacons(){
        return serverUrl + "getConfig.php";
    }

    public static String getBeaconsUrl(String shopName, String deviceId) {
        return String.format(
                Locale.US,
                serverUrl + "getConfig.php?id=%s&user=%s",
                shopName,
                deviceId
        );
    }

    public String getCode(){
        return serverUrl + "getCode.php";
    }

    public void setCurrentBeacon(BeaconDiscount beacon){
        currentSelectedBeacon = beacon;
    }

    public BeaconDiscount getCurrentSelectedBeacon(){
        return currentSelectedBeacon;
    }
    
    // region API tags
    public static final String STATUS_TAG = "status";

    public static final String DISCOUNT_ID_TAG = "discount_id";

    public static final String STORE_TAG = "store";

    public static final String BEACON_ID = "factory_id";

    public static final String DISCOUNT_TAG = "discountName";

    public static final String PRODUCT_TAG = "discountProduct";

    public static final String NEW_PRICE_TAG = "discountNewPrice";

    public static final String OLD_PRICE_TAG = "discountOldPrice";

    public static final String VALID_FROM_TAG = "discountValidFrom";

    public static final String VALID_TO_TAG = "discountValidTo";

    public static final String CODE_TAG = "code";
    //endregion
}
