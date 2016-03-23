package com.dinobikic.shopapp.adapters;

import com.dinobikic.shopapp.R;
import com.dinobikic.shopapp.models.BeaconDiscount2;

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
public class BeaconListDisplayAdapter extends ArrayAdapter<BeaconDiscount2> {

    private List<BeaconDiscount2> beacons;
    Context currentContext;

    public BeaconListDisplayAdapter(Context context, int resource, List<BeaconDiscount2> objects) {
        super(context, resource, objects);

        this.beacons = objects;
        this.currentContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.beacons_list_item, null);
        }

        BeaconDiscount2 currBeacon = this.beacons.get(position);

        if(currBeacon != null){
            TextView tvDiscountName = (TextView) convertView.findViewById(R.id.tvDiscountName);
            TextView tvOldPrice= (TextView) convertView.findViewById(R.id.tvOldPrice);
            TextView tvNewPrice = (TextView) convertView.findViewById(R.id.tvNewPrice);

            if(tvDiscountName!= null){
                tvDiscountName.setText(currBeacon.getDiscountName());
            }
            if(tvOldPrice!= null){
                tvOldPrice.setText(String.valueOf(currBeacon.getDiscountOldPrice()));
            }
            if(tvNewPrice!= null){
                tvNewPrice.setText(String.valueOf(currBeacon.getDiscountNewPrice()));
            }
        }

        return convertView;
    }

}
