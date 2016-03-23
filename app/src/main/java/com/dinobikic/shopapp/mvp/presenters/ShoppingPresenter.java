package com.dinobikic.shopapp.mvp.presenters;

import android.content.Intent;

/**
 * Created by dino on 23/03/16.
 */
public interface ShoppingPresenter extends BasePresenter {

    void onCreated(Intent intent);

    void getBeaconList();
}
