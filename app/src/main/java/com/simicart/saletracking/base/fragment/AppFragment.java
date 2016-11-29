package com.simicart.saletracking.base.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.simicart.saletracking.base.entity.AppData;

import java.util.HashMap;

/**
 * Created by Glenn on 11/29/2016.
 */

public class AppFragment extends Fragment {

    protected View rootView;
    protected AppData mData;
    protected HashMap<String, Object> mHashMapData;

    protected static final String KEY_DATA = "data";

    public static AppFragment newInstance(AppData data) {
        AppFragment fragment = new AppFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromBundle();
    }

    protected void getDataFromBundle() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mData = bundle.getParcelable("data");
            if (mData != null) {
                mHashMapData = mData.getData();
            }
            getArguments().remove("data");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("data",mData);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            getDataFromBundle();
            Log.e("AppFragment ","onActivityCreated RESTORE DATA ");
        } else {
            if (mData != null) {
                //returning from backstack, data is fine, do nothing
                Log.e("AppFragment ","onActivityCreated DATA NOOT NULL");
            } else {
                //newly created, compute data
                Log.e("AppFragment ","onActivityCreated DATA NULL");
            }
        }
    }

    public Object getValueWithKey(String key) {
        if (null != mHashMapData) {
            mHashMapData = getData();
        }
        if (null == mHashMapData) {
            return null;
        }
        return mHashMapData.get(key);
    }

    public HashMap<String, Object> getData() {
        if (null != mData) {
            return mData.getData();
        }
        return null;
    }

}
