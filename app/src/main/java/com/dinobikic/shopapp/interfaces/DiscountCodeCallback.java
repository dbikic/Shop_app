package com.dinobikic.shopapp.interfaces;

import com.dinobikic.shopapp.models.CodeResponse;

/**
 * Created by dino on 23/03/16.
 */
public interface DiscountCodeCallback {

    void onError();

    void onSuccess(CodeResponse codeResponse);

}
