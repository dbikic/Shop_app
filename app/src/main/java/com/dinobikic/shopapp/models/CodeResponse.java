package com.dinobikic.shopapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dino on 27/03/16.
 */
public class CodeResponse implements Serializable {

    @SerializedName("status")
    boolean status;

    @SerializedName("code")
    String code;

    public boolean isStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
