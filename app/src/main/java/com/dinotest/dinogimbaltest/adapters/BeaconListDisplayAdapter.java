package com.dinotest.dinogimbaltest.adapters;

import com.dinotest.dinogimbaltest.models.BeaconDiscount;
import com.dinotest.dinogimbaltest.R;

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
public class BeaconListDisplayAdapter extends ArrayAdapter<BeaconDiscount> {

    private List<BeaconDiscount> beacons;
    Context currentContext;

    public BeaconListDisplayAdapter(Context context, int resource, List<BeaconDiscount> objects) {
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

        BeaconDiscount currBeacon = this.beacons.get(position);

        if(currBeacon != null){
            TextView tvDiscountName = (TextView) convertView.findViewById(R.id.tvDiscountName);
            TextView tvOldPrice= (TextView) convertView.findViewById(R.id.tvOldPrice);
            TextView tvNewPrice = (TextView) convertView.findViewById(R.id.tvNewPrice);

            if(tvDiscountName!= null){
                tvDiscountName.setText(currBeacon.getDiscount());
            }
            if(tvOldPrice!= null){
                tvOldPrice.setText(currBeacon.getOldPrice());
            }
            if(tvNewPrice!= null){
                tvNewPrice.setText(currBeacon.getNewPrice());
            }
        }

        return convertView;
    }

}
