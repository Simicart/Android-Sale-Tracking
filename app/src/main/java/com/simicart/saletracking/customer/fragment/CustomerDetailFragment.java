package com.simicart.saletracking.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.customer.block.CustomerDetailBlock;
import com.simicart.saletracking.customer.controller.CustomerDetailController;

/**
 * Created by Glenn on 11/27/2016.
 */

public class CustomerDetailFragment extends AppFragment {

    protected CustomerDetailBlock mBlock;
    protected CustomerDetailController mController;
    protected String mCustomerID;

    public static CustomerDetailFragment newInstance(AppData data) {
        CustomerDetailFragment fragment = new CustomerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_detail, container, false);

        if (mData != null) {
            mCustomerID = (String) getValueWithKey("customer_id");
        }

        mBlock = new CustomerDetailBlock(rootView);
        mBlock.initView();
        if (mController == null) {
            mController = new CustomerDetailController();
            mController.setDelegate(mBlock);
            mController.setCustomerID(mCustomerID);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        mBlock.setOnCustomerOrdersClick(mController.getOnCustomerOrderClick());
        mBlock.setOnCustomerAddressesClick(mController.getOnCustomerAddressesClick());
        mBlock.setOnEditSummaryClick(mController.getOnEditSummaryClick());
        mBlock.setOnEditInfoClick(mController.getOnEditInfoClick());

        return rootView;
    }
}
