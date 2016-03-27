package com.dinobikic.shopapp.activities;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.models.Discount;
import com.dinobikic.shopapp.mvp.presenters.DiscountDetailsPresenter;
import com.dinobikic.shopapp.mvp.presenters.impl.DiscountDetailsPresenterImpl;
import com.dinobikic.shopapp.mvp.views.DiscountDetailsView;
import com.dinobikic.shopapp.utils.Constants;
import com.dinobikic.shopapp.utils.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscountDetailsActivity extends BaseActivity implements DiscountDetailsView {

    public static final String DISCOUNT_TAG = "discount";

    @Bind(R.id.tv_product)
    TextView tvProduct;

    @Bind(R.id.tv_old_price)
    TextView tvOldPrice;

    @Bind(R.id.tv_new_price)
    TextView tvNewPrice;

    @Bind(R.id.tv_discount)
    TextView tvDiscount;

    @Bind(R.id.tv_valid_to)
    TextView tvValidTo;

    @Bind(R.id.btn_discount_code)
    Button btnDiscountCode;

    @Bind(R.id.tv_code)
    TextView tvCode;

    @Bind(R.id.code_wrapper)
    View codeWrapper;

    private DiscountDetailsPresenter presenter;

    public static final Intent buildIntent(Context context, Discount discount) {
        Intent intent = new Intent(context, DiscountDetailsActivity.class);
        intent.putExtra(DISCOUNT_TAG, discount);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);
        ButterKnife.bind(this);

        presenter = new DiscountDetailsPresenterImpl(this);

        presenter.onCreated(getIntent());
    }

    // region DiscountDetailsView

    @Override
    public void initUI(Discount discount) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(discount.getDiscountName());
        tvProduct.setText(discount.getDiscountProduct());
        tvOldPrice.setText(StringUtils.formatBalance(discount.getDiscountOldPrice()));
        tvNewPrice.setText(StringUtils.formatBalance(discount.getDiscountNewPrice()));
        tvDiscount.setText(
                StringUtils.getPercentage(
                        discount.getDiscountOldPrice(),
                        discount.getDiscountNewPrice()
                )
        );
        tvValidTo.setText(discount.getDiscountValidTo());
    }

    @Override
    public void showDiscountButton() {
        btnDiscountCode.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDiscountCode(String code) {
        btnDiscountCode.setVisibility(View.GONE);
        codeWrapper.setVisibility(View.VISIBLE);
        tvCode.setText(code);
    }

    //endregion

    @OnClick(R.id.btn_discount_code)
    public void getDiscount() {
        presenter.getDiscount();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onFinishing();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onFinishing();
    }

    private void onFinishing() {
        if (presenter.isCodeActivated()) {
            Intent intent = new Intent();
            intent.putExtra(Constants.CODE_KEY, presenter.getNewDiscountCode());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            finish();
        }

    }

}