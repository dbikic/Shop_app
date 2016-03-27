package com.dinobikic.shopapp.mvp.interactors;

import com.dinobikic.shopapp.interfaces.BeaconsCallback;

/**
 * Created by dino on 23/03/16.
 */
public interface ShoppingInteractor extends BaseInteractor {

    void getBeacons(String shopName, BeaconsCallback beaconsCallback);
}
