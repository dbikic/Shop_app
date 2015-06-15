package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;


public class DiscountDetailsActivity extends Activity {

    private Globals globalValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);

        globalValues = new Globals();
        BeaconDiscount b = globalValues.getCurrentSelectedBeacon();

        Toast.makeText(getApplicationContext(), b.getDiscount(), Toast.LENGTH_LONG).show();
    }

}
