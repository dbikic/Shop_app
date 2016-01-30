package com.dinobikic.shopapp.utils;

import com.dinobikic.shopapp.ShopApplication;

import android.bluetooth.BluetoothAdapter;
import android.nfc.NfcAdapter;

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

    public static boolean isNFCEnabled() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(ShopApplication.getInstance());
        return nfcAdapter.isEnabled();
    }
}
