package com.simicart.saletracking.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.customer.block.ListAddressesBlock;
import com.simicart.saletracking.customer.controller.ListAddressesController;

/**
 * Created by Glenn on 12/3/2016.
 */

public class ListAddressesFragment extends AppFragment {

    protected ListAddressesBlock mBlock;
    protected ListAddressesController mController;
    protected String mCustomerID;

    public static ListAddressesFragment newInstance(AppData data) {
        ListAddressesFragment fragment = new ListAddressesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_addresses, container, false);

        if(mData != null) {
            mCustomerID = (String) getValueWithKey("customer_id");
        }

        mBlock = new ListAddressesBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new ListAddressesController();
            mController.setDelegate(mBlock);
            mController.setCustomerID(mCustomerID);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        return rootView;
    }
}
