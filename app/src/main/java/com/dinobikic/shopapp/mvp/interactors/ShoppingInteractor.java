package com.dinobikic.shopapp.mvp.interactors;

import com.dinobikic.shopapp.interfaces.GetBeaconsCallback;

/**
 * Created by dino on 23/03/16.
 */
public interface ShoppingInteractor extends BaseInteractor {

    String getBeacons(String shopName, GetBeaconsCallback getBeaconsCallback);
}
