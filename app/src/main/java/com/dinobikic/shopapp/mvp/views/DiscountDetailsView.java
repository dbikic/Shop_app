package com.dinobikic.shopapp.mvp.views;

import com.dinobikic.shopapp.models.Discount;

/**
 * Created by dino on 27/03/16.
 */
public interface DiscountDetailsView extends BaseView {

    void initUI(Discount discount);

    void showDiscountCode(String code);

    void scrollToDiscount();
}
