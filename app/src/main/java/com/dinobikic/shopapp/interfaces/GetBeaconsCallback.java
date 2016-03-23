package com.dinobikic.shopapp.interfaces;

import com.dinobikic.shopapp.models.StoreConfiguration;

/**
 * Created by dino on 23/03/16.
 */
public interface GetBeaconsCallback {

    void onError();

    void onSuccess(StoreConfiguration storeConfiguration);

}
