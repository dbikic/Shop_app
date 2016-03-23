package com.dinobikic.shopapp.mvp.interactors;


import retrofit.Callback;


/**
 * Created by dino on 30/01/16.
 */
public interface MainInteractor {

    void getContent(String qwe, Callback<String> listener);

}
