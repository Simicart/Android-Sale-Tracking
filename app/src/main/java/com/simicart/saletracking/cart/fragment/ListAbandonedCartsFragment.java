package com.simicart.saletracking.cart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.cart.block.ListAbandonedCartBlock;
import com.simicart.saletracking.cart.controller.ListAbandonedCartsController;

/**
 * Created by Glenn on 12/8/2016.
 */

public class ListAbandonedCartsFragment extends AppFragment {

    protected ListAbandonedCartBlock mBlock;
    protected ListAbandonedCartsController mController;

    public static ListAbandonedCartsFragment newInstance(AppData data) {
        ListAbandonedCartsFragment fragment = new ListAbandonedCartsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_abandoned_cart, container, false);

        mBlock = new ListAbandonedCartBlock(rootView);
        mBlock.initView();
        if (mController == null) {
            mController = new ListAbandonedCartsController();
            mController.setDelegate(mBlock);
            if (mData != null) {
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
        mBlock.setOnRefreshListener(mController.getOnRefreshPull());

        return rootView;
    }
}
