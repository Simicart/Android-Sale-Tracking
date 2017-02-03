package com.simicart.saletracking.base.block;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.delegate.AppDelegate;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.base.request.AppCollection;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/24/2016.
 */

public class AppBlock implements AppDelegate {

    protected View mView;
    protected Context mContext;
    protected View progressDialogView;
    protected ProgressDialog pd_loading;
    protected ViewGroup viewGroup;
    protected boolean isInitDialogLoading = false;
    protected ArrayList<Integer> listStatus;

    public AppBlock(View view) {
        mView = view;

        mContext = AppManager.getInstance().getCurrentActivity();

        viewGroup = ((ViewGroup) mView);

        initLoading();
    }

    public AppBlock() {

    }

    public void initView() {

    }

    protected void initLoading() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        progressDialogView = inflater.inflate(
                R.layout.view_loading, viewGroup,
                false);
        progressDialogView.setBackgroundColor(Color.WHITE);
    }

    protected void initDialogLoading() {
        pd_loading = ProgressDialog.show(mContext, null, null, true, false);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View loadingView = inflater.inflate(R.layout.view_loading, null);
        pd_loading.setContentView(loadingView);
        pd_loading.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd_loading.setCanceledOnTouchOutside(false);
        pd_loading.setCancelable(false);
        pd_loading.dismiss();
    }

    @Override
    public void showLoading() {
        if(mView instanceof LinearLayout) {
            viewGroup.addView(progressDialogView, 0);
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            progressDialogView.setLayoutParams(params);
            viewGroup.addView(progressDialogView);
        }
    }

    @Override
    public void dismissLoading() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewGroup.removeView(progressDialogView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        progressDialogView.startAnimation(fadeOut);
    }

    @Override
    public void showDialogLoading() {
        if (!isInitDialogLoading) {
            isInitDialogLoading = true;
            initDialogLoading();
        }

        pd_loading.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd_loading.show();
    }

    @Override
    public void dismissDialogLoading() {
        if (!isInitDialogLoading) {
            isInitDialogLoading = true;
            initDialogLoading();
        }
        pd_loading.dismiss();
    }

    @Override
    public void updateView(AppCollection collection) {

    }

}
