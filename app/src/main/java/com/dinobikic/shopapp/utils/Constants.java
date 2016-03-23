package com.dinobikic.shopapp.utils;


import com.dinobikic.shopapp.models.BeaconDiscount2;

import java.util.Locale;

public final class Constants {

    public static String serverUrl = "http://cons.riteh.hr/nfc/api/";

    public static BeaconDiscount2 currentSelectedBeacon ;

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

    public void setCurrentBeacon(BeaconDiscount2 beacon){
        currentSelectedBeacon = beacon;
    }

    public BeaconDiscount2 getCurrentSelectedBeacon(){
        return currentSelectedBeacon;
    }

}
