package com.dinotest.dinogimbaltest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Toast.makeText(MainActivity.this, "QWE", Toast.LENGTH_LONG).show();

/*
        shopIntent = new Intent(getApplicationContext(), BeaconActivity.class);

        s1 = (Button) findViewById(R.id.btnShop1);
        s2 = (Button) findViewById(R.id.btnShop2);
        s3 = (Button) findViewById(R.id.btnShop3);

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shopIntent.putExtra("shopName", "Shop 1");
                shopIntent.putExtra("shopId", 1);
                startActivity(shopIntent);
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shopIntent.putExtra("shopName", "Shop 2");
                shopIntent.putExtra("shopId", 2);
                startActivity(shopIntent);
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shopIntent.putExtra("shopName", "Shop 3");
                shopIntent.putExtra("shopId", 3);
                startActivity(shopIntent);
            }
        });
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
