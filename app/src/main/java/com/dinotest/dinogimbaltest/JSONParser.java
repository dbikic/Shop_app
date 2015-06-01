package com.dinotest.dinogimbaltest;

/**
 * Created by root on 6/1/15.
 */

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {
    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {

        // Making HTTP request
        try {

            // check for request method
            if (method == "POST") {
                Log.d("JSONParser", "POST request");

                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else {
                // request method is GET

                Log.d("JSONParser", "GET request");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    Log.d("PARAMS", paramString);
                    url += "?" + paramString;
                }

                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (UnsupportedEncodingException e) {
            Log.e("JSONParser", "UnsupportedEncodingException --- e: " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e("JSONParser", "ClientProtocolException --- e: " + e.toString());
        } catch (IOException e) {
            Log.e("JSONParser", "IOException --- e: " + e.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.d("JSON STRING", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        //
//        json=convertUTF8EncodedStringToNormalString(json);
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "JSONException e: " + e.toString());
        }

        // return JSON String
        return jObj;

    }

    public static String convertUTF8EncodedStringToNormalString(String utf8EncodedString)
    {
 /*utf8EncodedString = "\u0041\u0041\u0041\u0041\u0041";*/

        String normal = "";

        if(utf8EncodedString!=null)
            try
            {
                byte[] b = utf8EncodedString.getBytes("UTF-8");
                normal = new String(b);

            } catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }

        return normal;
    }
}

