package com.dinobikic.shopapp.interfaces;

import com.dinobikic.shopapp.models.StoreConfiguration;

/**
 * Created by dino on 23/03/16.
 */
public interface StoreCallback {

    void onError();

    void onSuccess(StoreConfiguration storeConfiguration);

}
