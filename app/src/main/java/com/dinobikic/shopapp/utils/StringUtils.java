package com.dinobikic.shopapp.utils;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.ShopApplication;

import android.support.annotation.StringRes;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by dino on 23/03/16.
 */
public class StringUtils {

    private StringUtils() {
    }

    public static String getString(@StringRes int stringRes) {
        return ShopApplication.getInstance().getString(stringRes);
    }


    public static String formatBalance(double decimalBalance) {
        NumberFormat decimalFormat = NumberFormat.getInstance();
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);

        return String.format(
                Locale.US,
                "%s kn",
                decimalFormat.format(decimalBalance)
        );
    }

    public static String getPercentage(double oldPrice, double newPrice) {
        double percentage = (newPrice / oldPrice) * 100;
        return Math.round(100 - percentage) + "%";
    }


    public static String getRssiValueForDisplay(int rssiValue) {
        if (rssiValue < 0) {
            return String.format(Locale.getDefault(), ShopApplication.getInstance().getString(R.string.rssiLabel), String.valueOf(rssiValue));
        } else {
            return String.format(Locale.getDefault(), ShopApplication.getInstance().getString(R.string.rssiLabel), String.valueOf(-rssiValue));
        }
    }
}
