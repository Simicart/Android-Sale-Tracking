package com.simicart.saletracking.cart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.entity.AppData;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.cart.block.AbandonedCartDetailBlock;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.cart.controller.AbandonedCartDetailController;

/**
 * Created by Glenn on 12/8/2016.
 */

public class AbandonedCartDetailFragment extends AppFragment {

    protected AbandonedCartDetailBlock mBlock;
    protected AbandonedCartDetailController mController;
    protected String mAbandonedCartID;

    public static AbandonedCartDetailFragment newInstance(AppData data) {
        AbandonedCartDetailFragment fragment = new AbandonedCartDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_abandoned_cart_detail, container, false);

        if(mData != null) {
            mAbandonedCartID = (String) getValueWithKey("cart_id");
        }

        mBlock = new AbandonedCartDetailBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new AbandonedCartDetailController();
            mController.setDelegate(mBlock);
            if(Utils.validateString(mAbandonedCartID)) {
                mController.setAbandonedCartID(mAbandonedCartID);
            }
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        return rootView;
    }
}
