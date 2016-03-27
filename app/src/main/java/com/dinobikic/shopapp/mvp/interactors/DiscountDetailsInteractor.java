package com.dinobikic.shopapp.mvp.interactors;

import com.dinobikic.shopapp.interfaces.DiscountCodeCallback;

/**
 * Created by dino on 27/03/16.
 */
public interface DiscountDetailsInteractor extends BaseInteractor {

    void getDiscount(int discountId, DiscountCodeCallback discountCodeCallback);

}
