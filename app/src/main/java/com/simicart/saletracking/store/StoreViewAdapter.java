package com.simicart.saletracking.store;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simicart.saletracking.R;
import com.simicart.saletracking.base.manager.AppManager;
import com.simicart.saletracking.common.AppColor;
import com.simicart.saletracking.common.Utils;
import com.simicart.saletracking.store.entity.StoreViewEntity;

import java.util.ArrayList;

/**
 * Created by Glenn on 11/29/2016.
 */

public class StoreViewAdapter extends BaseAdapter {

    protected ArrayList<StoreViewEntity> mListStoreViews;
    protected LayoutInflater inflater;

    public StoreViewAdapter(ArrayList<StoreViewEntity> listStoreViews) {
        inflater = LayoutInflater.from(AppManager.getInstance().getCurrentActivity());
        mListStoreViews = listStoreViews;
    }

    @Override
    public int getCount() {
        return mListStoreViews.size();
    }

    @Override
    public Object getItem(int position) {
        return mListStoreViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_store_view_item, parent, false);
        TextView tvStore = (TextView) convertView.findViewById(R.id.tv_store);
        tvStore.setTextColor(AppColor.getInstance().getBlackColor());

        StoreViewEntity storeViewEntity = mListStoreViews.get(position);
        String storeViewName = storeViewEntity.getStoreName();
        if(Utils.validateString(storeViewName)) {
            tvStore.setText(storeViewName);
        }

        return convertView;
    }
}
