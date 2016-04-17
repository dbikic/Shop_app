package com.dinobikic.shopapp.mvp.views;

import com.dinobikic.shopapp.models.Discount;

/**
 * Created by dino on 23/03/16.
 */
public interface ShoppingView extends BaseView {

    void initUI();

    void initStore(String store);

    void showDiscount(Discount beaconDiscount);

    void navigateToDiscountDetails(Discount discount);

    void requestEnableProtocol(String message);
}
