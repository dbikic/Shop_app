package com.dinotest.dinogimbaltest;

import com.dinotest.dinogimbaltest.utils.Events;
import com.gimbal.android.Gimbal;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import de.greenrobot.event.EventBus;

/**
 * Created by dino on 26/12/15.
 */
public class ShopApplication extends Application {

    protected static ShopApplication instance;

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
        init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(mReceiver);
    }

    private void init() {
        Gimbal.setApiKey(this, "b004f8c0-d82f-4809-8b0c-a3698e0b8d79");
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                if (state == BluetoothAdapter.STATE_OFF) {
                    EventBus.getDefault().post(new Events.BluetoothState(getString(R.string.bluetooth_error)));
                }
            }
        }
    };


}
