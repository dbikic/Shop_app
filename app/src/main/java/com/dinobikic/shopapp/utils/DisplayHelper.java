package com.dinobikic.shopapp.utils;

/**
 * Created by dino on 26/12/15.
 */
public class DisplayHelper {

    private DisplayHelper() {

    }

    public static String getPercentage(double oldPrice, double newPrice) {
        double percentage = (newPrice / oldPrice) * 100;
        return String.format("%.02f", 100 - percentage) + " %";
    }
}
