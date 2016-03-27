package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.adapters.DiscountsAdapter;
import com.dinobikic.shopapp.models.BeaconDiscount2;
import com.dinobikic.shopapp.mvp.presenters.ShoppingPresenter;
import com.dinobikic.shopapp.mvp.presenters.impl.ShoppingPresenterImpl;
import com.dinobikic.shopapp.mvp.views.ShoppingView;
import com.dinobikic.shopapp.utils.Constants;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingActivity extends BaseActivity implements ShoppingView {

    private static final String RSSIKEY = "rssiKey";

    @Bind(R.id.tv_shop_title)
    TextView tvShopTitle;

    @Bind(R.id.lv_discounts)
    ListView lvDiscounts;

    @Bind(R.id.empty_state)
    View emptyListView;

    private SharedPreferences sharedpreferences;

    int rssi;

    final static int REQUEST_ENABLE_BT = 1;

    private List<BeaconDiscount2> beaconDiscountList, seenBeacons;

    private DiscountsAdapter adapter;

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
        adapter = new DiscountsAdapter(this, R.layout.beacons_list_item, seenBeacons);
        lvDiscounts.setAdapter(adapter);
        lvDiscounts.setEmptyView(emptyListView);
        lvDiscounts.setOnItemClickListener(beaconListClick);
    }

    @Override
    public void requestEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    public void setStoreTitle(String storeTitle) {
        tvShopTitle.setText(storeTitle);
    }

    @Override
    public void showActiveDiscounts(ArrayList<BeaconDiscount2> beacons) {
        if (beacons != null && beacons.size() != 0) {
            adapter.addAll(beacons);
        }
    }

    @Override
    public void showDiscount(BeaconDiscount2 beaconDiscount) {
        adapter.add(beaconDiscount);
    }

    //endregion

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                presenter.getBeaconList();
            } else {
                showMessage(getString(R.string.without_bluetooth_app_doesnt_work));
            }
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

    int i = 0;
    @OnClick(R.id.tv_shop_title)
    public void addBeacon() {
        if (i == 0) {
            presenter.onBeaconDiscovered("qwe");
            i++;
        } else if (i == 1) {
            presenter.onBeaconDiscovered("qwe2");
            i++;
        } else if (i == 2) {
            presenter.onBeaconDiscovered("qwe3");
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
