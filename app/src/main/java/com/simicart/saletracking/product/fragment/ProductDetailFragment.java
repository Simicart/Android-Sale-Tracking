package com.simicart.saletracking.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.product.block.ProductDetailBlock;
import com.simicart.saletracking.product.controller.ProductDetailController;

/**
 * Created by Glenn on 12/7/2016.
 */

public class ProductDetailFragment extends AppFragment {

    protected ProductDetailBlock mBlock;
    protected ProductDetailController mController;
    protected String mProductID;

    public static ProductDetailFragment newInstance(AppData data) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        if(mData != null) {
            mProductID = (String) getValueWithKey("product_id");
        }

        mBlock = new ProductDetailBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new ProductDetailController();
            mController.setDelegate(mBlock);
            if(Utils.validateString(mProductID)) {
                mController.setProductID(mProductID);
            }
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        mBlock.setViewDetailDescriptionClick(mController.getOnDescriptionClick());
        mBlock.setViewDetailShortDescriptionClick(mController.getOnShortDescriptionClick());

        return rootView;
    }
}
