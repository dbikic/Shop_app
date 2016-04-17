package com.dinobikic.shopapp.mvp.views;

/**
 * Created by dino on 26/12/15.
 */
public interface BaseView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void showEnableProtocol(String message);

    void finishActivity();

    void onProtocolEnabled();
}
