package com.simicart.saletracking.base.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.simicart.saletracking.base.manager.AppManager;

/**
 * Created by Glenn on 11/27/2016.
 */

public class AppComponent {

    protected View rootView;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected String mKey;

    public AppComponent() {
        mContext = AppManager.getInstance().getCurrentActivity();
        mInflater = LayoutInflater.from(mContext);
    }

    public View createView() {
        return null;
    }

    public String getText() {
        return null;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }
}
