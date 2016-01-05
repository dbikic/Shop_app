package com.dinotest.dinogimbaltest.utils;

/**
 * Created by dino on 05/01/16.
 */
public class Events {

    public static class BluetoothState {

        private String state;

        public BluetoothState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }
}
