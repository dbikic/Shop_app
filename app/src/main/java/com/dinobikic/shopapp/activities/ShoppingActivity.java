package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.adapters.DiscountsAdapter;
import com.dinobikic.shopapp.dialogs.ShopInfoDialogFragment;
import com.dinobikic.shopapp.models.Discount;
import com.dinobikic.shopapp.mvp.presenters.ShoppingPresenter;
import com.dinobikic.shopapp.mvp.presenters.impl.ShoppingPresenterImpl;
import com.dinobikic.shopapp.mvp.views.ShoppingView;
import com.dinobikic.shopapp.utils.Constants;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
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


public  class ShoppingActivity extends BaseActivity implements ShoppingView {

    @Bind(R.id.tv_shop_title)
    TextView tvShopTitle;

    @Bind(R.id.tv_discovered_beacons)
    TextView tvDiscoveredBeacons;

    @Bind(R.id.lv_discounts)
    ListView lvDiscounts;

    @Bind(R.id.empty_state)
    View emptyListView;

    private List<Discount> seenBeacons;

    BluetoothLeScanner bluetoothLeScanner;

    private DiscountsAdapter adapter;

    private ShoppingPresenter presenter;

    private AdapterView.OnItemClickListener beaconListClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            presenter.onDiscountSelected(i);
        }
    };

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            presenter.onBeaconDiscovered(result.getRssi(), result.getDevice());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_shopping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_info) {
            ShopInfoDialogFragment dialogFragment = ShopInfoDialogFragment.newInstance(
                    presenter.getStoreConfiguration()
            );
            dialogFragment.show(getFragmentManager(), ShopInfoDialogFragment.SHOPING_INFO_DIALOG_FRAGMENT_TAG);

        } else if (item.getItemId() == android.R.id.home) {
            finishActivity();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public void finishActivity() {
        bluetoothLeScanner.stopScan(scanCallback);
        super.finishActivity();
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

    // region BaseView
    @Override
    public void onProtocolEnabled() {
        if (presenter.areProtocolsEnabled()) {
            presenter.getBeaconList();
        }
    }

    //endregion

    // region ShoppingView

    @Override
    public void initUI() {
        seenBeacons = new ArrayList<>();
        adapter = new DiscountsAdapter(this, R.layout.beacons_list_item, seenBeacons);
        lvDiscounts.setAdapter(adapter);
        lvDiscounts.setEmptyView(emptyListView);
        lvDiscounts.setOnItemClickListener(beaconListClick);

    }

    @Override
    public void initStore(String storeTitle) {
        tvShopTitle.setText(storeTitle);
        tvDiscoveredBeacons.setVisibility(View.VISIBLE);

        bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.startScan(scanCallback);
        }
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

    @Override
    public void requestEnableProtocol(String message) {
        showEnableProtocol(message);
    }

    //endregion

}
