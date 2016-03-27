package com.dinobikic.shopapp.utils;


import com.dinobikic.shopapp.models.Discount;

import java.util.Locale;

public final class Constants {

    public static String serverUrl = "http://cons.riteh.hr/nfc/api/";

    public static Discount currentSelectedBeacon ;

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

    public static String getCode(){
        return serverUrl + "getCode.php";
    }

    public void setCurrentBeacon(Discount beacon){
        currentSelectedBeacon = beacon;
    }

    public Discount getCurrentSelectedBeacon(){
        return currentSelectedBeacon;
    }

    public static final int REQUEST_CODE_DISCOUNT = 100;

    public static final String RSSI_KEY = "rssiKey";

    public static final String CODE_KEY = "code";

    public static final String POSITION_KEY = "position";
}
