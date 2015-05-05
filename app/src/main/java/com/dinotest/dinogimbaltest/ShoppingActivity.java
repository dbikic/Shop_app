package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ShoppingActivity extends Activity {

    private TextView txtShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        txtShop = (TextView) findViewById(R.id.txtShop);
        Intent shopIntent = getIntent();

        // dinonfc://dino/shop/X
        String shopName = shopIntent.getDataString().substring(20);
        txtShop.setText("Trgovina: " + shopName);
    }


}
