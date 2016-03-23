package com.dinobikic.shopapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dino on 23/03/16.
 */
public class BeaconUtils {


    private BeaconUtils() {
    }

    public static boolean isDiscountValid(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            Date dateFrom = format.parse(date1);
            Date dateTo = format.parse(date2);
            if (dateFrom.before(dateTo)) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
