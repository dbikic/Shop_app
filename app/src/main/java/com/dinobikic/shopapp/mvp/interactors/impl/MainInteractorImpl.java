package com.dinobikic.shopapp.mvp.interactors.impl;

import com.dinobikic.shopapp.mvp.interactors.MainInteractor;

import retrofit.Callback;

/**
 * Created by dino on 30/01/16.
 */
public class MainInteractorImpl implements MainInteractor {

    @Override
    public void getContent(String qwe, Callback<String> listener) {

/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);

        Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions("android");
        //asynchronous call
        call.enqueue(this);
        */
    }
}
