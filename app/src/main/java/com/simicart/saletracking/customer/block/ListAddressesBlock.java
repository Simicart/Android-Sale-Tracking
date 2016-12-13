package com.simicart.saletracking.customer.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.block.AppBlock;
import com.simicart.saletracking.base.request.AppCollection;
import com.simicart.saletracking.customer.component.AddressComponent;
import com.simicart.saletracking.customer.entity.AddressEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 12/3/2016.
 */

public class ListAddressesBlock extends AppBlock {

    protected LinearLayout llAddresses;

    public ListAddressesBlock(View view) {
        super(view);
    }

    @Override
    public void initView() {
        llAddresses = (LinearLayout) mView.findViewById(R.id.ll_addresses);
    }

    @Override
    public void updateView(AppCollection collection) {
        if (collection != null && collection.containKey("addresses")) {
            ArrayList<AddressEntity> listAddresses = (ArrayList<AddressEntity>) collection.getDataWithKey("addresses");
            if (listAddresses.size() > 0) {
                llAddresses.removeAllViewsInLayout();
                for (AddressEntity addressEntity : listAddresses) {
                    AddressComponent addressComponent = new AddressComponent();
                    addressComponent.setAddressEntity(addressEntity);
                    addressComponent.setShowEmail(true);
                    addressComponent.setShowTitle(true);
                    View addressView = addressComponent.createView();
                    if (addressView != null) {
                        llAddresses.addView(addressView);
                    }
                }
            } else {
                showEmptyMessage();
            }
        } else {
            showEmptyMessage();
        }
    }

    public void showEmptyMessage() {
        ((ViewGroup) mView).removeAllViewsInLayout();
        TextView tvEmpty = new TextView(mContext);
        tvEmpty.setTextColor(Color.BLACK);
        tvEmpty.setText("No addresses found");
        tvEmpty.setTypeface(null, Typeface.BOLD);
        tvEmpty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvEmpty.setGravity(Gravity.CENTER);
        tvEmpty.setLayoutParams(params);
        ((ViewGroup) mView).addView(tvEmpty);
    }

}
