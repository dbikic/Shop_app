package com.dinotest.dinogimbaltest.mvp.interactors;

/**
 * Created by dino on 30/12/15.
 */
public interface ConfigInteractor extends BaseInteractor {

    /**
     * Save RSSI value to shared preferences.
     *
     * @param rssi RSSI value.
     */
    void setRssi(int rssi);

    /**
     * Get RSSI from shared preferences.
     */
    int getRssi();
}
