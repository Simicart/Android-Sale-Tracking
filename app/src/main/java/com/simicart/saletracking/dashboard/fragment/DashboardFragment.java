package com.simicart.saletracking.dashboard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.dashboard.block.DashboardBlock;
import com.simicart.saletracking.dashboard.controller.DashboardController;

/**
 * Created by Glenn on 12/3/2016.
 */

public class DashboardFragment extends AppFragment {

    protected DashboardBlock mBlock;
    protected DashboardController mController;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mBlock = new DashboardBlock(rootView);
        mBlock.initView();
        if (mController == null) {
            mController = new DashboardController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        AppManager.getInstance().getMenuTopController().setController(mController);

        return rootView;
    }
}
