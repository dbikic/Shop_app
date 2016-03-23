package com.dinobikic.shopapp.networking;

import com.dinobikic.shopapp.models.StoreConfigurationResponse;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by dino on 30/01/16.
 */
public interface ApiService {

    @POST("/SingleHandler.ashx")
    @Headers("Accept: application/json")
    StoreConfigurationResponse getBetTypes(
    );

}