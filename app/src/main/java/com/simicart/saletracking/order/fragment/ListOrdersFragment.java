package com.simicart.saletracking.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
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

    public static ListOrdersFragment newInstance(AppData data) {
        ListOrdersFragment fragment = new ListOrdersFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);

        mBlock = new ListOrdersBlock(rootView);
        mBlock.initView();
        if (mController == null) {
            mController = new ListOrdersController();
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
        mBlock.setOnStatusFilterClick(mController.getOnStatusFilterClick());
        mBlock.setOnSortCLick(mController.getOnSortClick());
        mBlock.setOnTimeFilterClick(mController.getOnTimeFilterClick());

        AppManager.getInstance().getMenuTopController().setListOrdersController(mController);

        return rootView;
    }
}
