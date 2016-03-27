package com.dinobikic.shopapp.adapters;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.models.Discount;
import com.dinobikic.shopapp.utils.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 15.06.15..
 */
public class DiscountsAdapter extends ArrayAdapter<Discount> {

    private List<Discount> beacons;

    Context currentContext;

    public DiscountsAdapter(Context context, int resource, List<Discount> objects) {
        super(context, resource, objects);


        this.beacons = objects;
        this.currentContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.beacons_list_item, null);
        }

        Discount discount = this.beacons.get(position);

        if (discount != null) {
            TextView tvDiscount = (TextView) convertView.findViewById(R.id.tv_discount);
            TextView tvDiscountTitle = (TextView) convertView.findViewById(R.id.tv_discount_title);

            if (tvDiscount != null) {
                tvDiscountTitle.setText(discount.getDiscountName());
            }
            if (tvDiscountTitle != null) {
                tvDiscount.setText(
                        StringUtils.getPercentage(
                                discount.getDiscountOldPrice(),
                                discount.getDiscountNewPrice()
                        )
                );
            }
        }

        return convertView;
    }
}
