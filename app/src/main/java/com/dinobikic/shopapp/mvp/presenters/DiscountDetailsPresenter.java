package com.dinobikic.shopapp.mvp.presenters;

import android.content.Intent;

/**
 * Created by dino on 27/03/16.
 */
public interface DiscountDetailsPresenter extends BasePresenter {

    void onCreated(Intent intent);

    void getDiscount();

    boolean isCodeActivated();

    String getNewDiscountCode();
}
