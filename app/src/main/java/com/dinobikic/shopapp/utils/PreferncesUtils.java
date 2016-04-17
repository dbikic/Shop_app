package com.dinobikic.shopapp.utils;

import com.dinobikic.shopapp.ShopApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dino on 30/12/15.
 */
public class PreferncesUtils {

    public static final String RSSI_KEY = "rssiKey";

    public static final int DEFAULT_RSSI_VALUE = -100;

    private static SharedPreferences prefs;

    private static SharedPreferences.Editor edit;

    private PreferncesUtils() {
    }

    public static int getRssi() {
        return getIntFromPreferences(ShopApplication.getInstance(), RSSI_KEY, DEFAULT_RSSI_VALUE);
    }

    public static void setRssi(int rssi) {
        saveIntToPreferences(ShopApplication.getInstance(), rssi, RSSI_KEY);
    }

    /**
     * This method returns SharedPreferences object.
     *
     * @param ctx
     * @return
     */
    private static SharedPreferences getSharedPrefs(Context ctx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs;
    }


    /**
     * This method returns int object from preferences.
     *
     * @param ctx          Context
     * @param name         object name
     * @param defaultValue default value
     * @return
     */
    private static int getIntFromPreferences(Context ctx, String name, int defaultValue) {

        prefs = getSharedPrefs(ctx);
        return prefs.getInt(name, defaultValue);

    }

    /**.
     * This method saves int object in shared preferences.
     *
     * @param ctx  context
     * @param save int object to save
     * @param name Name of the object in shared preferences
     */
    private static void saveIntToPreferences(Context ctx, int save, String name) {

        prefs = getSharedPrefs(ctx);

        edit = prefs.edit();
        edit.putInt(name, save);
        edit.apply();

    }

}
