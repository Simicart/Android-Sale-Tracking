package com.simicart.saletracking.bestseller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.bestseller.block.BestSellersBlock;
import com.simicart.saletracking.bestseller.controller.BestSellersController;

/**
 * Created by Glenn on 12/9/2016.
 */

public class BestSellersFragment extends AppFragment {

    protected BestSellersBlock mBlock;
    protected BestSellersController mController;

    public static BestSellersFragment newInstance() {
        return new BestSellersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bestseller, container, false);

        mBlock = new BestSellersBlock(rootView);
        mBlock.initView();
        if (mController == null) {
            mController = new BestSellersController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        mBlock.setOnListScroll(mController.getOnListScroll());
        mBlock.setOnNextPage(mController.getOnNextPageClick());
        mBlock.setOnPreviousPage(mController.getOnPreviousPageClick());

        return rootView;
    }
}
