package com.dinobikic.shopapp.mvp.interactors;

import com.dinobikic.shopapp.interfaces.StoreCallback;

/**
 * Created by dino on 23/03/16.
 */
public interface ShoppingInteractor extends BaseInteractor {

    void getStoreConfiguration(String shopId, StoreCallback storeCallback);
}
