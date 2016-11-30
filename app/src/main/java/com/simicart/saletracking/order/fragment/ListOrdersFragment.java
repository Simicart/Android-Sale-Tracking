package com.simicart.saletracking.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.order.block.ListOrdersBlock;
import com.simicart.saletracking.order.controller.ListOrdersController;

/**
 * Created by Glenn on 11/26/2016.
 */

public class ListOrdersFragment extends AppFragment {

    protected ListOrdersBlock mBlock;
    protected ListOrdersController mController;

    public static ListOrdersFragment newInstance() {
        return new ListOrdersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);

        mBlock = new ListOrdersBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new ListOrdersController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        mBlock.setOnListScroll(mController.getOnListScroll());
        mBlock.setOnNextPage(mController.getOnNextPageClick());
        mBlock.setOnPreviousPage(mController.getOnPreviousPageClick());

        AppManager.getInstance().getMenuTopController().setListOrdersController(mController);

        return rootView;
    }
}
