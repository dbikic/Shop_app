package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.adapters.BeaconListDisplayAdapter;
import com.dinobikic.shopapp.models.BeaconDiscount;
import com.dinobikic.shopapp.mvp.presenters.ShoppingPresenter;
import com.dinobikic.shopapp.mvp.presenters.impl.ShoppingPresenterImpl;
import com.dinobikic.shopapp.mvp.views.ShoppingView;
import com.dinobikic.shopapp.utils.Constants;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShoppingActivity extends BaseActivity implements ShoppingView {

    private static final String RSSIKEY = "rssiKey";

    @Bind(R.id.tvShop)
    TextView tvShop;

    @Bind(R.id.lvBeacons)
    ListView lvBeacons;

    private int rssi;

    private SharedPreferences sharedpreferences;

    private BluetoothAdapter btAdapter;

    final static int REQUEST_ENABLE_BT = 1;

    private List<BeaconDiscount> beaconDiscountList, seenBeacons;

    private BeaconListDisplayAdapter adapter;

    // Progress Dialog
    private ProgressDialog pDialog;

    private Constants globalValues;

    private ShoppingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shopping);
        ButterKnife.bind(this);

        presenter = new ShoppingPresenterImpl(this);

        presenter.onCreated(getIntent());
    }

    // region ShoppingView

    @Override
    public void initUI() {
        globalValues = new Constants();

        beaconDiscountList = new ArrayList<>();
        seenBeacons = new ArrayList<>();

        adapter = new BeaconListDisplayAdapter(this, R.layout.beacons_list_item, seenBeacons);

        lvBeacons.setAdapter(adapter);
        lvBeacons.setOnItemClickListener(beaconListClick);

//        Intent shopIntent = getIntent();

        // dinonfc://dino/shop/X
/*
        GetConfig gc = new GetConfig();
        gc.execute();
*/
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(getApplication(), "Bluetooth nije podržan!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (btAdapter.isEnabled()) {
                presenter.getBeaconList();
//                GetConfig gc = new GetConfig();
//                gc.execute();
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    @Override
    public void requestEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    //endregion

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                showMessage("Bluetooth upaljen!");
            } else {
                showMessage("Bez bluetooth-a aplikacija ne radi...");
            }
        }

        if (btAdapter.isEnabled()) {
            presenter.getBeaconList();
//            GetConfig gc = new GetConfig();
//            gc.execute();
        }
    }

    public void beaconSearcher() {

        sharedpreferences = getSharedPreferences("DinoShoppApp", Context.MODE_PRIVATE);
        if (sharedpreferences.contains(RSSIKEY)) {
            rssi = sharedpreferences.getInt(RSSIKEY, 0);
        } else {
            rssi = -50;
        }

//        if (beaconSighting.getRSSI() > rssi) {
//            Beacon foundBeacon = beaconSighting.getBeacon();
//
//            for (int i = 0; i < beaconDiscountList.size(); i++) {
//
//                if (foundBeacon.getIdentifier().equals(beaconDiscountList.get(i).getId())) {
//                    if (!beaconDiscountList.get(i).getSeen()) {
//                        seenBeacons.add(beaconDiscountList.get(i));
//                        beaconDiscountList.get(i).setSeen();
//
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        }
//6HU1-R7XS5
    }


    private AdapterView.OnItemClickListener beaconListClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            globalValues.setCurrentBeacon(seenBeacons.get(i));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), DiscountDetailsActivity.class);
                    startActivity(intent);
                }
            });
        }
    };


    @Override
    public void onBackPressed() {
        finish();
    }

    public void changeTitle(Context c, String newTitle) {

    }

}
