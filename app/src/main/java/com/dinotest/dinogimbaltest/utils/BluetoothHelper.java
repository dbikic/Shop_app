package com.dinotest.dinogimbaltest.utils;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by dino on 05/01/16.
 */
public class BluetoothHelper {


    private BluetoothHelper() {

    }

    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }
}
