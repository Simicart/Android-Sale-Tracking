package com.simicart.saletracking.customer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.customer.block.ListCustomersBlock;
import com.simicart.saletracking.customer.controller.ListCustomersController;

/**
 * Created by Glenn on 11/30/2016.
 */

public class ListCustomersFragment extends AppFragment {

    protected ListCustomersBlock mBlock;
    protected ListCustomersController mController;

    public static ListCustomersFragment newInstance(AppData data) {
        ListCustomersFragment fragment = new ListCustomersFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customers, container, false);

        mBlock = new ListCustomersBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new ListCustomersController();
            mController.setDelegate(mBlock);
            if(mData != null) {
                mController.setData(mData.getData());
            }
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        mBlock.setOnListScroll(mController.getOnListScroll());
        mBlock.setOnNextPage(mController.getOnNextPageClick());
        mBlock.setOnPreviousPage(mController.getOnPreviousPageClick());
        mBlock.setOnSearchClick(mController.getOnSearchClick());

        return rootView;
    }
}
