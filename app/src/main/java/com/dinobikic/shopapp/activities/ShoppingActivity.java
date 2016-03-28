package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.adapters.DiscountsAdapter;
import com.dinobikic.shopapp.dialogs.ShopInfoDialogFragment;
import com.dinobikic.shopapp.models.Discount;
import com.dinobikic.shopapp.models.StoreConfiguration;
import com.dinobikic.shopapp.mvp.presenters.ShoppingPresenter;
import com.dinobikic.shopapp.mvp.presenters.impl.ShoppingPresenterImpl;
import com.dinobikic.shopapp.mvp.views.ShoppingView;
import com.dinobikic.shopapp.utils.Constants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Bind(R.id.tv_shop_title)
    TextView tvShopTitle;

    @Bind(R.id.tv_discovered_beacons)
    TextView tvDiscoveredBeacons;

    @Bind(R.id.lv_discounts)
    ListView lvDiscounts;

    @Bind(R.id.empty_state)
    View emptyListView;

    private SharedPreferences sharedpreferences;

    int rssi;

    private List<Discount> beaconDiscountList, seenBeacons;

    private DiscountsAdapter adapter;

    private ShoppingPresenter presenter;

    private AdapterView.OnItemClickListener beaconListClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            presenter.onDiscountSelected(i);
        }
    };

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
        beaconDiscountList = new ArrayList<>();
        seenBeacons = new ArrayList<>();
        adapter = new DiscountsAdapter(this, R.layout.beacons_list_item, seenBeacons);
        lvDiscounts.setAdapter(adapter);
        lvDiscounts.setEmptyView(emptyListView);
        lvDiscounts.setOnItemClickListener(beaconListClick);
    }

    @Override
    public void setStoreTitle(String storeTitle) {
        tvShopTitle.setText(storeTitle);
        tvDiscoveredBeacons.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDiscount(Discount beaconDiscount) {
        adapter.add(beaconDiscount);
    }

    @Override
    public void navigateToDiscountDetails(Discount discount) {
        Intent intent = DiscountDetailsActivity.buildIntent(this, discount);
        startActivityForResult(intent, Constants.REQUEST_CODE_DISCOUNT);
    }

    //endregion


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shopping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
//            Toast.makeText(ShoppingActivity.this, "Info", Toast.LENGTH_SHORT).show();
//            AlertDialog alertDialog = new AlertDialog.Builder(this)
//                    .setTitle(R.string.shop_details)
//                    .setMessage(StringUtils.getPercentage())
//                    .setPositiveButton(getString(R.string.ok), null)
//                    .create();
//            alertDialog.show();
            ShopInfoDialogFragment dialogFragment = ShopInfoDialogFragment.newInstance(
                    presenter.getStoreConfiguration()
            );
            dialogFragment.show(getFragmentManager(), "");
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                presenter.getBeaconList();
            } else {
                showMessage(getString(R.string.without_bluetooth_app_doesnt_work));
            }
        } else if (requestCode == Constants.REQUEST_CODE_DISCOUNT) {
            if (resultCode == RESULT_OK && data != null) {
                presenter.onCodeReceived(data.getStringExtra(Constants.CODE_KEY));
            }
        }
    }

    public void beaconSearcher() {

        sharedpreferences = getSharedPreferences("DinoShoppApp", Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Constants.RSSI_KEY)) {
            rssi = sharedpreferences.getInt(Constants.RSSI_KEY, 0);
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
    }

    int i = 0;
    @OnClick(R.id.tv_shop_title)
    public void addBeacon() {
        if (i == 0) {
            presenter.onBeaconDiscovered("6HU1-R7XS5");
            i++;
        } else if (i == 1) {
            presenter.onBeaconDiscovered("PPCN-QWM7G");
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
