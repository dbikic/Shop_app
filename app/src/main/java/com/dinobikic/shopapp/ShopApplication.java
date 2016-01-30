package com.dinobikic.shopapp;

import com.dinobikic.shopapp.utils.Events;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

/**
 * Created by dino on 26/12/15.
 */
public class ShopApplication extends Application {

    protected static ShopApplication instance;

    private final BroadcastReceiver brBluetooth = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                if (state == BluetoothAdapter.STATE_OFF) {
                    EventBus.getDefault().post(new Events.BluetoothState(getString(R.string.bluetooth_error)));
                }
            }
        }
    };

    private final BroadcastReceiver brNFC = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)) {
                EventBus.getDefault().post(new Events.NFCStateChange());
            }
        }
    };

    private final BroadcastReceiver btReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // rssi: intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                Toast.makeText(ShopApplication.this, "Found device " + device.getName(), Toast.LENGTH_SHORT).show();

            }
        }
    };

    public static ShopApplication getInstance() {
        return instance;
    }

    public static void setInstance(ShopApplication instance) {
        ShopApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        initReceivers();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(brBluetooth);
        unregisterReceiver(brNFC);
    }

    private void initReceivers() {

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(brBluetooth, filter);

        filter = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
        registerReceiver(brNFC, filter);

        filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(btReceiver, filter);
    }


}
