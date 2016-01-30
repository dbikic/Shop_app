package com.dinobikic.shopapp.utils;

/**
 * Created by dino on 26/12/15.
 */
public class DisplayHelper {

    private DisplayHelper() {

    }

    public static String getPercentage(String oldPrice, String newPrice) {
        float oldP = Float.parseFloat(oldPrice);
        float newP = Float.parseFloat(newPrice);
        float percentage = (newP / oldP) * 100;

        return String.format("%.02f", 100 - percentage) + " %";
    }
}
