package com.simicart.saletracking.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.order.block.OrderDetailBlock;
import com.simicart.saletracking.order.controller.OrderDetailController;

/**
 * Created by Glenn on 11/29/2016.
 */

public class OrderDetailFragment extends AppFragment {

    protected OrderDetailBlock mBlock;
    protected OrderDetailController mController;
    protected String mOrderID;

    public static OrderDetailFragment newInstance(AppData data) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_detail, container, false);

        if(mData != null) {
            mOrderID = (String) getValueWithKey("order_id");
        }

        mBlock = new OrderDetailBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new OrderDetailController();
            mController.setDelegate(mBlock);
            mController.setOrderID(mOrderID);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        return rootView;
    }
}
