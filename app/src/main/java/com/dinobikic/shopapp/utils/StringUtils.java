package com.dinobikic.shopapp.utils;

import com.dinobikic.shopapp.ShopApplication;

import android.support.annotation.StringRes;

/**
 * Created by dino on 23/03/16.
 */
public class StringUtils {

    private StringUtils() {
    }

    public static String getString(@StringRes int stringRes) {
        return ShopApplication.getInstance().getString(stringRes);
    }

}
