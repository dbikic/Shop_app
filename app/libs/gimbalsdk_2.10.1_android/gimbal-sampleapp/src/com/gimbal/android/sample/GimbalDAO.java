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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.gimbal.android.sample.GimbalEvent.TYPE;

public class GimbalDAO {
    public static final String GIMBAL_NEW_EVENT_ACTION = "GIMBAL_EVENT_ACTION";
    public static final String PLACE_MONITORING_PREFERENCE = "pref_place_monitoring";
    public static final String SHOW_OPT_IN_PREFERENCE = "pref_show_opt_in";
    private static final String EVENTS_KEY = "events";

    // --------------
    // OPT IN
    // --------------

    public static boolean showOptIn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SHOW_OPT_IN_PREFERENCE, true);
    }

    public static void setOptInShown(Context context) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(SHOW_OPT_IN_PREFERENCE, false);
        editor.commit();

    }

    // --------------
    // GIMBAL EVENTS
    // --------------

    public static List<GimbalEvent> getEvents(Context context) {
        List<GimbalEvent> events = new ArrayList<GimbalEvent>();
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String jsonString = prefs.getString(EVENTS_KEY, null);
            if (jsonString != null) {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GimbalEvent event = new GimbalEvent();
                    event.setType(TYPE.valueOf(jsonObject.getString("type")));
                    event.setTitle(jsonObject.getString("title"));
                    event.setDate(new Date(jsonObject.getLong("date")));
                    events.add(event);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static void setEvents(Context context, List<GimbalEvent> events) {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            JSONArray jsonArray = new JSONArray();
            for (GimbalEvent event : events) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", event.getType().name());
                jsonObject.put("title", event.getTitle());
                jsonObject.put("date", event.getDate().getTime());
                jsonArray.put(jsonObject);
            }
            String jstr = jsonArray.toString();
            Editor editor = prefs.edit();
            editor.putString(EVENTS_KEY, jstr);
            editor.commit();

            // Notify activity
            Intent intent = new Intent();
            intent.setAction(GIMBAL_NEW_EVENT_ACTION);
            context.sendBroadcast(intent);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
