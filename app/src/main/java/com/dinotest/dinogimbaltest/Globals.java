package com.dinotest.dinogimbaltest;

import android.app.Application;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class Globals extends Application {

    //    debug tags and msges
    private static String ETAG = "ERROR";
    private static String DTAD = "SHOP_APP";
    //
    private List<NameValuePair> globals = new ArrayList<>();
    private String host = "http://www.zae.riteh.hr/scf/scfmobile_bikic/";


    public Globals() {

    }

    public void setValue(String key, String value) {
        if (!globals.contains(key)) {
            globals.add(new BasicNameValuePair(key, value));
        }
    }

    public String getValue(String tag) {
        for (int i = 0; i < globals.size(); i++) {
            if (globals.get(i).getName().equals(tag)) {
                return globals.get(i).getValue();
            }
        }
        return "";
    }

    public String getHost() {
        return host;
    }
}
