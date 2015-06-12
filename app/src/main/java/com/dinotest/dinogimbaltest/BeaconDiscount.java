package com.dinotest.dinogimbaltest;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11.06.15..
 */
public class BeaconDiscount {
    private List<NameValuePair> list = new ArrayList<NameValuePair>();
    private Boolean seen;

    // TODO staviti tagove od apia
    String ID_TAG = "factory_id";
    String DISCOUNT_TAG = "discountName";
    String PRODUCT_TAG = "discountProduct";
    String NEW_PRICE_TAG = "discountNewPrice";
    String OLD_PRICE_TAG = "discountOldPrice";
    String VALID_FROM_TAG = "discountValidFrom";
    String VALID_TO_TAG = "discountValidTo";


    public BeaconDiscount(List<NameValuePair> _list, boolean seen)
    {
        this.list = _list;
        this.seen = false;
    }

    public String getValue(String value){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(value)){
                return list.get(i).getValue();
            }
        }
        return "";
    }

    public boolean getSeen(){ return this.seen;}

    public void setSeen(){ this.seen = true;}

    public String getId(){
        return getValue(ID_TAG);
    }

    public String getDiscount(){
        return getValue(DISCOUNT_TAG);
    }

    public String getProduct(){
        return getValue(PRODUCT_TAG);
    }

    public String getNewPrice(){
        return getValue(NEW_PRICE_TAG);
    }

    public String getOldPrice(){
        return getValue(OLD_PRICE_TAG);
    }

    public String getValidFrom(){
        return getValue(VALID_FROM_TAG);
    }

    public String getVALID_TO_TAG(){
        return getValue(VALID_TO_TAG);
    }

}
