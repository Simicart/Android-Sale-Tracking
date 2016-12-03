package com.simicart.saletracking.dashboard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.fragment.AppFragment;

/**
 * Created by Glenn on 12/3/2016.
 */

public class DashboardFragment extends AppFragment {

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return rootView;
    }
}
