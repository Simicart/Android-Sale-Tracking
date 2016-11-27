package com.simicart.saletracking.menu.top;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;

/**
 * Created by Glenn on 11/26/2016.
 */

public class MenuTopController {

    protected Toolbar mToolbar;
    protected Context mContext;
    protected View rootView;
    protected ImageView ivMenu;
    protected TextView tvTitle;
    protected Spinner spStore;
    protected RelativeLayout rlStore;
    protected boolean isOnDetail = false;

    public MenuTopController(Toolbar toolbar) {
        mToolbar = toolbar;
        mToolbar.setBackgroundColor(Color.parseColor("#fc9900"));
        mToolbar.setContentInsetsAbsolute(0, 0);
        mToolbar.setPadding(0, 0, 0, 0);
        mContext = AppManager.getInstance().getCurrentActivity();
        initView();
    }

    protected void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rootView = inflater.inflate(R.layout.view_menu_top, null);

        ivMenu = (ImageView) rootView.findViewById(R.id.iv_menu);
        ivMenu.setImageResource(R.drawable.ic_menu);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setTextColor(AppColor.getInstance().getWhiteColor());

        spStore = (Spinner) rootView.findViewById(R.id.sp_store);

        rlStore = (RelativeLayout) rootView.findViewById(R.id.rl_store);

        mToolbar.addView(rootView);
    }

    public void showMenuTop(boolean is_show) {
        if (is_show) {
            mToolbar.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void showStorePicker(boolean is_show) {
        if (is_show) {
            rlStore.setVisibility(View.VISIBLE);
        } else {
            rlStore.setVisibility(View.GONE);
        }
    }

    public void setOnDetail(boolean isDetail) {
        if(isDetail) {
            ivMenu.setImageResource(R.drawable.ic_back);
            isOnDetail = true;
        } else {
            ivMenu.setImageResource(R.drawable.ic_menu);
            isOnDetail = false;
        }
    }

}
