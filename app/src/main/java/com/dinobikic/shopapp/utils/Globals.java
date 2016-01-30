package com.dinobikic.shopapp.utils;


import com.dinobikic.shopapp.models.BeaconDiscount;

public final class Globals {

    private String serverUrl = "http://meri-test.webuda.com/shop_app_backend/api/";
    public static BeaconDiscount currentSelectedBeacon ;

    public Globals() { }

    public String getBeacons(){
        return this.serverUrl + "getConfig.php";
    }

    public String getCode(){
        return this.serverUrl + "getCode.php";
    }

    public void setCurrentBeacon(BeaconDiscount beacon){
        currentSelectedBeacon = beacon;
    }

    public BeaconDiscount getCurrentSelectedBeacon(){
        return currentSelectedBeacon;
    }
}