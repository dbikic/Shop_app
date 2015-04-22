/**
 * Copyright (C) 2014 Gimbal, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of Gimbal, Inc.
 *
 * The following sample code illustrates various aspects of the Gimbal SDK.
 *
 * The sample code herein is provided for your convenience, and has not been
 * tested or designed to work on any particular system configuration. It is
 * provided AS IS and your use of this sample code, whether as provided or
 * with any modification, is at your own risk. Neither Gimbal, Inc.
 * nor any affiliate takes any liability nor responsibility with respect
 * to the sample code, and disclaims all warranties, express and
 * implied, including without limitation warranties on merchantability,
 * fitness for a specified purpose, and against infringement.
 */
package com.gimbal.android.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceManager;

public class OptInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optin);
    }

    public void onEnableClicked(View view) {
        GimbalDAO.setOptInShown(getApplicationContext());

        PlaceManager.getInstance().startMonitoring();
        CommunicationManager.getInstance().startReceivingCommunications();

        // Setup Push Communication
        String gcmSenderId = null; // <--- SET THIS STRING TO YOUR PUSH SENDER ID HERE (Google API project #) ##
        registerForPush(gcmSenderId);

        finish();
    }

    private void registerForPush(String gcmSenderId) {
        if (gcmSenderId != null) {
            Gimbal.registerForPush(gcmSenderId);
        }
    }

    public void onNotNowClicked(View view) {
        GimbalDAO.setOptInShown(getApplicationContext());
        PlaceManager.getInstance().stopMonitoring();
        finish();
    }

    public void onPrivacyPolicyClicked(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://your-privacy-policy-url")));
    }

    public void onTermsOfServiceClicked(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://your-terms-of-use-url")));
    }
}
