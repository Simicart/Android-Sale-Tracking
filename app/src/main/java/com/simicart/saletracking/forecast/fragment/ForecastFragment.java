package com.simicart.saletracking.forecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.forecast.block.ForecastBlock;
import com.simicart.saletracking.forecast.controller.ForecastController;

/**
 * Created by Martial on 1/16/2017.
 */

public class ForecastFragment extends AppFragment {

    protected ForecastBlock mBlock;
    protected ForecastController mController;

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        mBlock = new ForecastBlock(rootView);
        mBlock.initView();
        if(mController == null) {
            mController = new ForecastController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        return rootView;
    }
}
