package com.simicart.saletracking.menutop;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.controller.AppController;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.order.controller.ListOrdersController;
import com.simicart.saletracking.store.apdater.StoreViewAdapter;
import com.simicart.saletracking.store.entity.StoreViewEntity;

import java.util.ArrayList;

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
    protected ArrayList<StoreViewEntity> mListStoreViews;
    protected AppController mController;
    protected boolean isFirstRun = true;

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
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnDetail) {
                    AppManager.getInstance().backToPreviousFragment();
                } else {
                    AppManager.getInstance().openDrawer();
                }
            }
        });

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvTitle.setTextColor(Color.WHITE);

        spStore = (Spinner) rootView.findViewById(R.id.sp_store);
        spStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isFirstRun) {
                    AppManager.getInstance().setStoreID(mListStoreViews.get(i).getStoreID());
                    mController.onStart();
                } else {
                    isFirstRun = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rlStore = (RelativeLayout) rootView.findViewById(R.id.rl_store);
        rlStore.setVisibility(View.INVISIBLE);

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
            rlStore.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isOnDetail() {
        return isOnDetail;
    }

    public void setOnDetail(boolean isDetail) {
        if (isDetail) {
            ivMenu.setImageResource(R.drawable.ic_back);
            isOnDetail = true;
        } else {
            ivMenu.setImageResource(R.drawable.ic_menu);
            isOnDetail = false;
        }
    }

    public ArrayList<StoreViewEntity> getListStoreViews() {
        return mListStoreViews;
    }

    public void setListStoreViews(ArrayList<StoreViewEntity> listStoreViews) {
        if (listStoreViews == null) {
            rlStore.setVisibility(View.INVISIBLE);
        } else {
            mListStoreViews = new ArrayList<>();

            // Create Default Store View
            StoreViewEntity defaultStoreView = new StoreViewEntity();
            defaultStoreView.setStoreID("0");
            defaultStoreView.setStoreName("All Stores");
            mListStoreViews.add(defaultStoreView);

            mListStoreViews.addAll(listStoreViews);
            rlStore.setVisibility(View.VISIBLE);
            StoreViewAdapter storeViewAdapter = new StoreViewAdapter(mListStoreViews);
            spStore.setAdapter(storeViewAdapter);
        }
    }

    public void setController(AppController controller) {
        mController = controller;
    }
}
