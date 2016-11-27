package com.simicart.saletracking.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.customer.block.CustomerDetailBlock;
import com.simicart.saletracking.customer.controller.CustomerDetailController;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailFragment extends Fragment {

    protected CustomerDetailBlock mBlock;
    protected CustomerDetailController mController;

    public static CustomerDetailFragment newInstance() {
        return new CustomerDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_detail, container, false);

        mBlock = new CustomerDetailBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new CustomerDetailController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        return rootView;
    }
}
