package com.simicart.saletracking.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.product.block.ListProductsBlock;
import com.simicart.saletracking.product.controller.ListProductsController;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ListProductsFragment extends AppFragment {

    protected ListProductsBlock mBlock;
    protected ListProductsController mController;

    public static ListProductsFragment newInstance(AppData data) {
        ListProductsFragment fragment = new ListProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_products, container, false);

        mBlock = new ListProductsBlock(rootView);
        mBlock.initView();
        if (mController == null) {
            mController = new ListProductsController();
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

        AppManager.getInstance().getMenuTopController().setController(mController);

        return rootView;
    }
}
