package com.simicart.saletracking.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.fragment.AppFragment;
import com.simicart.saletracking.login.block.LoginBlock;
import com.simicart.saletracking.login.controller.LoginController;

/**
 * Created by Glenn on 11/24/2016.
 */

public class LoginFragment extends AppFragment {

    protected LoginBlock mBlock;
    protected LoginController mController;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mBlock = new LoginBlock(rootView);
        mBlock.initView();
        if (mController == null) {
            mController = new LoginController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        mBlock.onTryDemoClick(mController.getOnTryDemoClick());
        mBlock.onLoginClick(mController.getOnLoginClick());

        return rootView;
    }
}
