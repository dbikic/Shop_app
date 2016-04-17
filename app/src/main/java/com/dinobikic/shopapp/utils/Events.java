package com.dinobikic.shopapp.utils;

/**
 * Created by dino on 05/01/16.
 */
public class Events {

    public static class EnableProtocol {

        private String state;

        public EnableProtocol(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }

    public static class NFCStateChange {

    }
}
