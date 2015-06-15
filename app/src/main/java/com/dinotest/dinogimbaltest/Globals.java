package com.dinotest.dinogimbaltest;


public final class Globals {

    private String serverUrl = "http://meri-test.webuda.com/shop_app_backend/api/";
    public static BeaconDiscount currentSelectedBeacon ;

    public Globals() {

    }

    public String getBeacons(){
        return this.serverUrl + "getConfig.php?id=";
    }

    public void setCurrentBeacon(BeaconDiscount beacon){
        currentSelectedBeacon = beacon;
    }

    public BeaconDiscount getCurrentSelectedBeacon(){
        return currentSelectedBeacon;
    }
}
