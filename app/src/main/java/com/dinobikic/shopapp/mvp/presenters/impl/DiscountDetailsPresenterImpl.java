package com.dinobikic.shopapp.mvp.presenters.impl;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.activities.DiscountDetailsActivity;
import com.dinobikic.shopapp.interfaces.DiscountCodeCallback;
import com.dinobikic.shopapp.models.CodeResponse;
import com.dinobikic.shopapp.models.Discount;
import com.dinobikic.shopapp.mvp.interactors.DiscountDetailsInteractor;
import com.dinobikic.shopapp.mvp.interactors.impl.DiscountDetailsInteractorImpl;
import com.dinobikic.shopapp.mvp.presenters.DiscountDetailsPresenter;
import com.dinobikic.shopapp.mvp.views.DiscountDetailsView;
import com.dinobikic.shopapp.utils.StringUtils;

import android.content.Intent;

/**
 * Created by dino on 27/03/16.
 */
public class DiscountDetailsPresenterImpl implements DiscountDetailsPresenter {

    DiscountDetailsView view;

    DiscountDetailsInteractor interactor;

    Discount discount;

    boolean isCodeActivated;

    public DiscountDetailsPresenterImpl(DiscountDetailsView view) {
        this.view = view;
        this.interactor = new DiscountDetailsInteractorImpl();
    }

    @Override
    public void onCreated(Intent intent) {
        Discount discount = (Discount) intent.getSerializableExtra(DiscountDetailsActivity.DISCOUNT_TAG);

        if (discount != null) {
            this.discount = discount;
            view.initUI(discount);

            if (discount.isDiscountActivated()) {
                view.showDiscountCode(discount.getCode());
            }
        }
    }

    @Override
    public void getDiscount() {
        view.showProgress();
        interactor.getDiscount(discount.getDiscountId(), new DiscountCodeCallback() {
            @Override
            public void onError() {
                view.hideProgress();
                view.showMessage(StringUtils.getString(R.string.error));
            }

            @Override
            public void onSuccess(CodeResponse codeResponse) {
                if (codeResponse != null && codeResponse.isStatus()) {
                    isCodeActivated = true;
                    discount.setCode(codeResponse.getCode());
                    view.showDiscountCode(codeResponse.getCode());
                    view.scrollToDiscount();
                    view.hideProgress();
                    view.showMessage(StringUtils.getString(R.string.code_received));
                } else {
                    onError();
                }
            }

        });
    }

    @Override
    public boolean isCodeActivated() {
        return isCodeActivated;
    }

    @Override
    public String getNewDiscountCode() {
        return discount.getCode();
    }

    @Override
    public void cancel() {

    }
}
